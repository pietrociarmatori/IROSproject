package it.pietrociarmatori.model.helpers;
// Offro un interfaccia utilizzabile da sviluppatori di view per essere notificati di errori nel GmailWatcher e/o UvicornLauncher
public interface ListenerThread {
    void notify(Throwable e);
}
