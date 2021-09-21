package fr.tp.coo;

public class Generateur extends Block {
    Etat e1;
    Sortie job;
    public Generateur(double tg){
        e1 = new Etat("s",tg);
        etat_courant = e1;
        etat_initial = e1;
        ensembleEtat.put("s",e1);
        job = new Sortie("job","bool");
        listeSortie.add(job);
        this.setNom("Generator");
    }

    @Override
    public String toString() {
        return "Generateur{" +
                "listeEntree=" + listeEntree +
                ", listeSortie=" + listeSortie +
                ", ensembleEtat=" + ensembleEtat +
                ", etat_initial=" + etat_initial +
                ", tr=" + tr +
                ", nom='" + nom +
                '}';
    }

    @Override
    public void externe(Sortie s) {
    }

    @Override
    public void interne(Entree e ) {

    }


    @Override
    public void sortie() {
        job.setValeur(1);
    }


}
