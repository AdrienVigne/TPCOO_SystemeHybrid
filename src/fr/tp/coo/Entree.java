package fr.tp.coo;

public class Entree implements Observateur{
    private String nom;
    private String type;
    private double valeur = 0;
    private Boolean flag = Boolean.FALSE;

    public Entree(String nom, String type){
        this.nom = nom;
        this.type = type;

    }

    @Override
    public String toString() {
        return "Entree{" +
                "nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", valeur=" + valeur +
                ", flag=" + flag +
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
    }
    public Boolean getFlag(){
        return flag;
    }
    @Override
    public void recevoirNotification(Observable obs, double val) {
        System.out.println("obs : " + obs + " val : " +val);
        this.valeur = val;
        this.flag = Boolean.TRUE;

    }
}
