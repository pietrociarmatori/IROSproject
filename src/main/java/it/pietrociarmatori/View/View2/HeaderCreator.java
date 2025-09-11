package it.pietrociarmatori.View.View2;

public class HeaderCreator {
    public static void createHeader(String nome, String cognome, String ruolo, String dip, String matr, String pass){
        String line = "-------------------------------------------------------";
        System.out.println(line+"                                                                                            Dati profilo:");
        System.out.println(line);
        System.out.println("-------  --------------  --------------  --------------                                                                                            -)Nome: "+nome);
        System.out.println("|     |  |            |  |            |  |            |                                                                                            -)Cognome: "+cognome);
        System.out.println("|     |  |     ---    |  |      |     |  |            |                                                                                            -)Ruolo: "+ruolo);
        System.out.println("|     |  |            |  |      |     |  |       -----|                                                                                            -)Dipartimento: "+dip);
        System.out.println("|     |  |       -----|  |      |     |  |            |                                                                                            -)Matricola: "+matr);
        System.out.println("|     |  |            |  |      |     |  |-----       |                                                                                            -)Password: "+pass);
        System.out.println("|     |  |     |      |  |      |     |  |            |");
        System.out.println("|     |  |     |      |  |            |  |            |");
        System.out.println("-------  --------------  --------------  --------------");
        System.out.println(line);
        System.out.println(line);
    }
}
