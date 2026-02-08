package it.pietrociarmatori.Model.Helpers;

import it.pietrociarmatori.Exceptions.ConnectionPoolException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

// Questa classe è un singleton che si occupa di gestire la quantità di connessioni utilizzabili dagli utenti del DB
// inizialmente sono 5 (che è il minimo) e possono arrivare ad un massimo di 10 se ci sono molti utenti che richiedono
// le connessioni. Il metodo releaseConnection() valuta se le connessioni attualmente utilizzabili sono uno spreco
// oppure no. Si ottiene così uno stack di connessioni che varia in grandezza a seconda del congestionamento.

public class ConnectionPoolMYSQL implements ConnectionPoolInterface {

    // gli array non sono di per loro thread safe, ma sincronizzo l'accesso, perciò risulta una pratica corretta
    // altrimenti esiste "Collections.synchronizedList"
    protected static List<Connection> utilizzabili;
    protected static List<Connection> inUso;
    protected static final int INITIAL_POOL_SIZE = 5;
    protected static final int MAX_POOL_SIZE = 10;

    // Il LazyContainer permette al singleton di essere thread safe.
    // Non è possibile avere due thread che allo stesso tempo inizializzino questa inner-class.
    private static class LazyContainer{
        public final static ConnectionPoolMYSQL singleton;

        static {
            try {
                singleton = new ConnectionPoolMYSQL();
            } catch (ConnectionPoolException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected ConnectionPoolMYSQL() throws ConnectionPoolException {
        utilizzabili = new ArrayList<>(INITIAL_POOL_SIZE);
        inUso = new ArrayList<>(INITIAL_POOL_SIZE);

        for(int i = 0; i < INITIAL_POOL_SIZE; i++){
            Connection conn = createConnection();
            utilizzabili.add(conn);
        }
    }

    protected Connection createConnection() throws ConnectionPoolException {
        Connection conn = null;
        try(InputStream input = new FileInputStream("resources/db.properties")){

            Properties properties = new Properties();
            properties.load(input);

            String connection_url = properties.getProperty("CONNECTION_URL");
            String user = properties.getProperty("LOGIN_USER");
            String passwd = properties.getProperty("LOGIN_PASS");

            conn = DriverManager.getConnection(connection_url, user, passwd);

        } catch (IOException | SQLException e){
            throw new ConnectionPoolException("Impossibile creare nuova connessione");
        }

        return conn;
    }


    @Override
    public synchronized Connection getConnection() throws ConnectionPoolException {
        Connection conn = null;
        try {

            // calcolo del numero attuale di connessioni
            int totalConnections = utilizzabili.size() + inUso.size();

            // se non ci sono connessioni disponibili ma possiamo ancora crearne
            if (utilizzabili.isEmpty() && totalConnections < MAX_POOL_SIZE) {
                conn = createConnection();
                inUso.add(conn);
                return conn;
            }

            // se possiamo prendere una connessione già pronta
            if (!utilizzabili.isEmpty()) {
                conn = utilizzabili.remove(utilizzabili.size() - 1);
                inUso.add(conn);
                return conn;
            }

            // nessuna connessione disponibile e pool pieno -> attendi con meccanismo busy waiting
            while (conn == null) {

                wait(200); // sospende il thread

                if (!utilizzabili.isEmpty()) {
                    conn = utilizzabili.remove(utilizzabili.size() - 1);
                    inUso.add(conn);
                }
            }
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // buona pratica
            throw new RuntimeException("Thread interrotto mentre aspettava una connessione libera", e);
        }catch(ConnectionPoolException e){
            throw new ConnectionPoolException("Impossibile ottenere connessione con database");
        }

        return conn;
    }


    @Override
    public synchronized void releaseConnection(Connection conn) throws ConnectionPoolException {
        try {
            if (utilizzabili.size() >= INITIAL_POOL_SIZE) {
                conn.close();
            } else {
                utilizzabili.add(conn);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Impossibile chiudere connessione");
        } finally {
            inUso.remove(conn);
            this.notify(); // risveglia un thread che è in attesa
        }
    }


    public static ConnectionPoolMYSQL getInstance(){return LazyContainer.singleton;}
}