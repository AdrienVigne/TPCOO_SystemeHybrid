package fr.tp.coo;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Block implements ModeleAtomique{
    ArrayList<Entree> listeEntree;
    ArrayList<Sortie> listeSortie;
    HashMap<String,Etat> ensembleEtat;
    Etat etat_courant;
    Etat etat_initial;
    double e;
    double tl;
    double tn;
    double tr;

    public Block(){
        listeEntree = new ArrayList<>();
        listeSortie = new ArrayList<>();
        ensembleEtat = new HashMap<>();
    }
    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }

    public double getTl() {
        return tl;
    }

    public void setTl(double tl) {
        this.tl = tl;
    }

    public double getTn() {
        return tn;
    }

    public void setTn(double tn) {
        this.tn = tn;
    }

    public double getTr() {
        return tr;
    }

    public void setTr(double tr) {
        this.tr = tr;
    }

    String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public double avancement() {
        return ensembleEtat.get(etat_courant.nom).getTemps();
    }

    @Override
    public void init() {
        etat_courant = etat_initial;
        listeSortie.forEach(s -> s.setValeur(Double.NaN));
        listeEntree.forEach(e -> e.setValeur(Double.NaN));
    }

    public Boolean entreeImpactee(){
        Boolean rtr = Boolean.FALSE;
        for(Entree e : listeEntree){
            rtr |= e.getFlag();
        }
        return rtr;

    }

    @Override
    public void conflict() {
        this.externe();
    }

    @Override
    public void finCycle() {
        this.listeEntree.forEach(entree -> {entree.setValeur(Double.NaN);entree.deactivateFlag();});
        this.listeSortie.forEach(sortie -> sortie.setValeur(Double.NaN));
    }
}
