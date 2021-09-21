package fr.tp.coo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Buffer B = new Buffer();
        Generateur G = new Generateur(2);
        Processeur P = new Processeur(3);
        G.job.ajoutObservateur(B.job);
        P.done.ajoutObservateur(B.done);
        B.req.ajoutObservateur(P.req);

        ArrayList<Block> listeComposant = new ArrayList<>();
        listeComposant.add(B);
        listeComposant.add(G);
        listeComposant.add(P);
        System.out.println(listeComposant);

        listeComposant.forEach(Block::init);
        listeComposant.forEach(comp -> comp.setTr(comp.avancement()));

        ArrayList<Double> listeTr = new ArrayList<>();
        double tr_min = 0;
        listeComposant.forEach(comp -> listeTr.add(comp.getTr()));
        System.out.println(listeTr);
        tr_min = Collections.min(listeTr);
        int minIndex = listeTr.indexOf(tr_min);

        System.out.println("Index : "+minIndex+" composant associé : "+ listeComposant.get(minIndex).nom +" tr_min : "+tr_min);
    }
}
