package fr.tp.coo;

public class Etat {
    double temps;
    String nom;
    public Etat(String nom,double temps){
        this.nom = nom;
        this.temps =temps;
    }

    @Override
    public String toString() {
        return "Etat{" +
                "temps=" + temps +
                ", nom='" + nom + '\'' +
                '}';
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
