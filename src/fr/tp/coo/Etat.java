package fr.tp.coo;

public abstract class Etat {
    double temps;
    String nom;
    public Etat(double tmps,String nom){
        this.temps = tmps;
        this.nom = nom;
    }

    public double getTemps() {
        return temps;
    }

    public void setTemps(double temps) {
        this.temps = temps;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
