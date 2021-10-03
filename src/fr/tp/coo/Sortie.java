package fr.tp.coo;

import java.util.ArrayList;

public class Sortie implements Observable{
    String nom;
    String type;
    double valeur = 0;
    ArrayList<Observateur> listeObservateur  = new ArrayList<>();

    public Sortie(String nom, String type){
        this.nom = nom;
        this.type = type;

    }

    @Override
    public String toString() {
        return "Sortie{" +
                "nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", valeur=" + valeur +
                '}';
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
        if(!Double.isNaN(this.valeur)){
            this.notifierTous();
        }
//        System.out.println("Val NaN");
    }

    public void setValeur(double valeur,double temps) {
        this.valeur = valeur;
        if(!Double.isNaN(this.valeur)){
            this.notifierTous(temps);
        }
//        System.out.println("Val NaN");
    }

    @Override
    public void ajoutObservateur(Observateur obs) {
        this.listeObservateur.add(obs);
    }

    @Override
    public void supressionObservateur(Observateur obs) {
        this.listeObservateur.remove(obs);
    }

    @Override
    public void notifierTous() {
        this.listeObservateur.forEach(obs -> obs.recevoirNotification(this,this.valeur));
    }

    @Override
    public void notifierTous(double t){
        this.listeObservateur.forEach(obs -> obs.recevoirNotification(this,this.valeur,t));
    }


}
