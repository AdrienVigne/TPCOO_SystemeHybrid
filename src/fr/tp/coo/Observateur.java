package fr.tp.coo;

public interface Observateur {
    void recevoirNotification(Observable obs,double val);
    void recevoirNotification(Observable obs,double val,double temps);

}
