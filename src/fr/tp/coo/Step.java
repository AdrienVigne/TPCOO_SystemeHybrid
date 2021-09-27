package fr.tp.coo;

public class Step extends Block {
    Etat bas;
    Etat attente, fin;
    Etat haut;
    Sortie val;
    double xi, xf;

    public Step(double xi, double xf, double tf) {
        this.xi = xi;
        this.xf = xf;
        bas = new Etat("bas", 0);
        attente = new Etat("attente", tf);
        haut = new Etat("haut", 0);
        fin = new Etat("fin", Double.POSITIVE_INFINITY);
        ensembleEtat.put("bas", bas);
        ensembleEtat.put("attente", attente);
        ensembleEtat.put("haut", haut);
        ensembleEtat.put("fin", fin);

        val = new Sortie("val", "double");
        listeSortie.add(val);

        etat_initial = bas;
        etat_courant = bas;

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
        if (etat_courant == bas) {
            etat_courant = attente;
        }
        if (etat_courant == attente) {
            etat_courant = haut;
        }
        if(etat_courant == haut){
            etat_courant = fin;
        }
    }

    @Override
    public void sortie() {
        if (etat_courant == bas) {
            val.setValeur(xi);

        }
        if (etat_courant == haut || etat_courant == fin) {
            val.setValeur(xf);
        }
        System.out.println(this.nom + " : " + val.valeur);

    }
}
