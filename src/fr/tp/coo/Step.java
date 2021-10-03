package fr.tp.coo;

public class Step extends Block {
    Etat bas;
    Etat init;
    Etat haut;
    Sortie val;
    double xi, xf, valeur_courante;


    public Step(double xi, double xf, double tf) {
        this.xi = xi;
        this.xf = xf;
        this.valeur_courante = xi;
        init = new Etat("init", 0);
        bas = new Etat("bas", tf);
        haut = new Etat("haut", Double.POSITIVE_INFINITY);
        this.ensembleEtat.put("init", init);
        this.ensembleEtat.put("bas", bas);
        this.ensembleEtat.put("haut", haut);
        val = new Sortie("val", "double");
        this.listeSortie.add(val);
        this.etat_initial = init;
        this.setNom("Stepper");
    }

    @Override
    public String toString() {
        return "Step{" +
                "bas=" + bas +
                ", haut=" + haut +
                ", val=" + val +
                ", xi=" + xi +
                ", xf=" + xf +
                '}';
    }

    @Override
    public void externe() {
    }

    @Override
    public void interne() {
        if (this.etat_courant == init) {
            this.etat_courant = bas;
        }
        if (this.etat_courant == bas) {
            this.etat_courant = haut;
            this.valeur_courante = this.xf;
        }
    }

    @Override
    public void sortie() {
//        if (this.etat_courant == bas || this.etat_courant == init) {
//            this.val.setValeur(this.valeur_courante);
//
//        }
//        if (this.etat_courant == haut ) {
//            this.val.setValeur(this.valeur_courante);
//        }
        this.val.setValeur(this.valeur_courante);
        System.out.println(this.nom + " : " + val.valeur);

    }
}
