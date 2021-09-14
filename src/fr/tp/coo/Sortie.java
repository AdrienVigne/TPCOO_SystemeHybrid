package fr.tp.coo;

public class Sortie implements Observable{
    String nom;
    String type;
    double valeur = 0;

    public Sortie(String nom, String type){
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
        if(!Double.isNaN(this.valeur)){
            this.notifierTous();
        }
        System.out.println("Val NaN");
    }

    @Override
    public void ajoutObservateur(Observateur obs) {
        listeObservateur.add(obs);
    }

    @Override
    public void supressionObservateur(Observateur obs) {
        listeObservateur.remove(obs);
    }

    @Override
    public void notifierTous() {
        listeObservateur.forEach(obs -> obs.recevoirNotification(this,this.valeur));
    }
}
