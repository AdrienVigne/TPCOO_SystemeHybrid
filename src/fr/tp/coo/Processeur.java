package fr.tp.coo;

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

    }


    @Override
    public void externe(Sortie s) {
        if (etat_courant==e1 && req.getFlag()){
            etat_courant = e2;
        }
    }

    @Override
    public void interne(Entree e) {
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
