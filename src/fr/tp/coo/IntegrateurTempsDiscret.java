package fr.tp.coo;


public class IntegrateurTempsDiscret extends Block {
    Etat attente, sortie;
    double val, dval, tm, hstep;
    Entree pval;
    Sortie res;

    public IntegrateurTempsDiscret(double hstep) {
        this.hstep = hstep;
        attente = new Etat("attente", hstep);
        sortie = new Etat("sortie",0);
        ensembleEtat.put("attente",attente);
        ensembleEtat.put("sortie",sortie);
        pval = new Entree("Derive de val","double");
        listeEntree.add(pval);
        res = new Sortie("Resultat integration","double");
        val = 0;
        dval = 0;
        tm = 0;

        etat_initial = attente;
    }

    @Override
    public void externe() {
        if(pval.getFlag()){
            this.val += this.e * this.dval;
            this.tm = this.e;
            this.dval = pval.getValeur();
            this.etat_courant = sortie;
            System.out.println("dval : "+this.dval+" tm : "+this.tm);
        }

    }

    @Override
    public void interne() {
        if(this.etat_courant==attente){
//            System.out.println("tm : "+this.tm+" dval : "+this.dval);
            this.val += (this.hstep-this.tm)*this.dval;
            if(this.tm != 0){
                this.tm = 0;
            }
            this.etat_courant = sortie;
        }else if (this.etat_courant == sortie){
            this.etat_courant = attente;
        }


    }

    @Override
    public void sortie() {
        if(this.etat_courant == sortie){
//            System.out.println("Sortie : "+this.val);
            res.setValeur(this.val);
        }

    }
}
