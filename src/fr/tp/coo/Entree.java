package fr.tp.coo;

public class Entree implements Observateur{
    String nom;
    String type;
    double valeur = 0;

    public Entree(String nom, String type){
        this.nom = nom;
        this.type = type;

    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public double getValeur() {
        return valeur;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }


    @Override
    public void recevoirNotification(Observable obs, double val) {
        System.out.println("obs : " + obs + " val : " +val);
        this.valeur = val;

    }
}
