package fr.tp.coo;

public class Constante extends Block{
    Etat init,fin;
    Sortie constante;
    double val;

    public Constante(double C){
        init = new Etat("init",0);
        fin = new Etat("fin",Double.POSITIVE_INFINITY);
        ensembleEtat.put("init",init);
        ensembleEtat.put("fin",fin);
        this.val = C;
        constante = new Sortie("constante","double");
        this.listeSortie.add(constante);
        this.etat_initial = init;
    }

    @Override
    public void externe() {
    }

    @Override
    public void interne() {
        if(this.etat_courant == init){
            this.etat_courant = fin;
        }
    }

    @Override
    public void sortie() {
        if(this.etat_courant == init){
            this.constante.setValeur(this.val);
        }
    }
}
