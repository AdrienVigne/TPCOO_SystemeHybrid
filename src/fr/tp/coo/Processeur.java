package fr.tp.coo;

import javax.swing.*;

public class Processeur extends Block {
    Etat e1;
    Etat e2;
    Entree req;
    Sortie done;
    public Processeur(double tp){
        e1 = new Etat("free",Double.POSITIVE_INFINITY);
        e2 = new Etat("busy",tp);
        ensembleEtat.put("free",e1);
        ensembleEtat.put("busy",e2);
        etat_initial = e1;
        req = new Entree("req","bool");
        done = new Sortie("done","bool");
        listeEntree.add(req);
        listeSortie.add(done);
        this.setNom("Processor");
    }

    @Override
    public String toString() {
        return "Processeur{" +
                ", tr=" + tr +
                ", nom='" + nom + '\'' +
                '}';
    }

    @Override
    public void externe() {
        if (etat_courant==e1 && req.getFlag()){
            etat_courant = e2;
        }
    }

    @Override
    public void interne() {
        if(etat_courant == e2){
            etat_courant = e1;
        }
    }


    @Override
    public void sortie() {
        if (etat_courant==e2){
            done.setValeur(1);
        }
    }


}
