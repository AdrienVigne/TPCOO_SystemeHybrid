package fr.tp.coo;

import java.util.ArrayList;

public class adder4I extends Block{
    Etat attente,somme;
    Entree e1,e2,e3,e4;
    Sortie add;
    double res;

    public adder4I(){
        attente = new Etat("attente",Double.POSITIVE_INFINITY);
        somme = new Etat("somme",0);
        ensembleEtat.put("attente",attente);
        ensembleEtat.put("somme",somme);

        e1 = new Entree("e1","double");
        e2 = new Entree("e2","double");
        e3 = new Entree("e3","double");
        e4 = new Entree("e4","double");

        listeEntree.add(e1);
        listeEntree.add(e2);
        listeEntree.add(e3);
        listeEntree.add(e4);

        add = new Sortie("add","double");
        listeSortie.add(add);

        res = 0;

        etat_initial = attente;

    }

    @Override
    public void externe() {
        if (etat_courant == attente){
            for(Entree e : listeEntree){
                if(e.getFlag()){
                    this.res += e.getValeur();
                }
            }
            System.out.println("resultat au cours du temps : "+this.res);
            etat_courant = somme;
        }
    }

    @Override
    public void interne() {
        if (etat_courant==somme){
            etat_courant=attente;
        }

    }

    @Override
    public void sortie() {
        if(this.etat_courant == somme) {
            System.out.println("Sortie du adder : " + this.res);
            add.setValeur(this.res);
//            add.setValeur(this.res, this.tl);
        }
    }
}
