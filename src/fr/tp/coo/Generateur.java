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
                ", tr=" + tr +
                ", nom='" + nom +
                '}';
    }

    @Override
    public void externe() {
    }

    @Override
    public void interne() {

    }


    @Override
    public void sortie() {
        job.setValeur(1);
    }


}
