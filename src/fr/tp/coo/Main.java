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

        double t = 0;
        double tfin=10;

        ArrayList<Block> listeComposant = new ArrayList<>();
        listeComposant.add(B);
        listeComposant.add(G);
        listeComposant.add(P);
        System.out.println(listeComposant);
        listeComposant.forEach(Block::init);
        listeComposant.forEach(comp -> comp.setTr(comp.avancement()));


        while (t < tfin)
        {
            ArrayList<Block> listeComposantImms = new ArrayList<>();
            ArrayList<Double> listeTr = new ArrayList<>();
            double tr_min = 0;
            listeComposant.forEach(comp -> listeTr.add(comp.getTr()));
            //System.out.println(listeTr);
            tr_min = Collections.min(listeTr);
            int minIndex = listeTr.indexOf(tr_min);

            for(Block comp : listeComposant)
                if(tr_min == comp.getTr())
                    listeComposantImms.add(comp);

            t = t + tr_min;

            for(Block comp : listeComposant)
            {
                comp.setE(comp.getE() + tr_min);
                comp.setTr(comp.getTr() - tr_min);
            }
            for(Block comp : listeComposantImms)
                comp.sortie();

            ArrayList<Entree> listeEntreeIns = new ArrayList<>();
            for(Entree e : listeEntreeIns)
               if(e.getFlag())
                   listeEntreeIns.add(e);

           /* for(Block comp : listeComposant)
            {
                if(listeComposantImms.contains(comp) && )
                {

                }
                else if(!listeComposantImms.contains(comp) &&))
                {

                }
                else if(listeComposantImms.contains(comp) &&)
            }*/

            // System.out.println("Index : "+minIndex+" composant associé : "+ listeComposant.get(minIndex).nom +" tr_min : "+tr_min);
        }
    }
}
