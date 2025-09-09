package it.pietrociarmatori.Model.Helpers;
// Offro un interfaccia utilizzabile da sviluppatori di view per essere notificati di errori nel GmailWatcher e/o UvicornLauncher
public interface ListenerThread {
    void notify(Throwable e);
}
