package fr.tp.coo;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

public class IntegrateurEventDiscret extends Block {
    Etat attente, sortie;
    double deltaX, tm, hstep, val, valpoint;
    Entree pval;
    Sortie res;

    public IntegrateurEventDiscret(double deltaX) {
        this.deltaX = deltaX;
        this.tm = 0;
        this.hstep = Double.POSITIVE_INFINITY;
        this.val = 0;
        this.valpoint = 0;
        attente = new Etat("attente", hstep);
        sortie = new Etat("sortie", 0);
        this.ensembleEtat.put("attente", attente);
        this.ensembleEtat.put("sortie", sortie);
        pval = new Entree("Nouvelle derivée", "double");
        listeEntree.add(pval);
        res = new Sortie("Valeur de l'intégrale", "double");
        listeSortie.add(res);

        this.etat_initial = attente;

    }

    @Override
    public void externe() {
        if (pval.getFlag()) {
            this.val += this.valpoint * this.e;
            this.valpoint = pval.getValeur();
            this.tm = this.e;
            this.hstep = abs(this.deltaX) / abs(this.valpoint);
            this.attente.setTemps(this.hstep);
            if(this.valpoint!=0.0){
                this.deltaX = abs(this.deltaX)*signum(this.valpoint);
            }

        }
    }

    @Override
    public void interne() {
        if(this.etat_courant == attente){
            this.val += this.deltaX;
            this.etat_courant = sortie;
        }else if(this.etat_courant == sortie){
            this.etat_courant = attente;
        }

    }

    @Override
    public void sortie() {
        if(this.etat_courant == sortie){
            this.res.setValeur(this.val);
        }

    }
}
