package fr.tp.coo;

public class Buffer extends Block {
    Etat a, b, c;
    Sortie req;
    Entree job, done;
    double q = 0;

    public Buffer() {
        a = new Etat("a", Double.POSITIVE_INFINITY);
        b = new Etat("b", 0);
        c = new Etat("c", Double.POSITIVE_INFINITY);
        ensembleEtat.put("a", a);
        ensembleEtat.put("b", b);
        ensembleEtat.put("c", c);

        req = new Sortie("req", "bool");
        listeSortie.add(req);

        job = new Entree("job", "bool");
        done = new Entree("done", "bool");
        listeEntree.add(job);
        listeEntree.add(done);

        etat_initial = a;

        this.setNom("Buffer");
    }

    @Override
    public String toString() {
        return "Buffer{" +
                ", tr=" + tr +
                ", nom='" + nom + '\'' +
                '}';
    }


    @Override
    public void externe() {
        if (etat_courant == a && job.getFlag()) {
            q++;
            etat_courant = b;
        }
        if (etat_courant == c) {
            if (job.getFlag()) {
                q++;
            }
            if (done.getFlag()) {
                if (q == 0) {
                    etat_courant = a;
                } else {
                    etat_courant = b;
                }
            }
        }
    }

    @Override
    public void interne() {
        if (etat_courant == b) {
            q--;
            etat_courant = c;
        }
    }


    @Override
    public void sortie() {
        if (etat_courant == b) {
            req.setValeur(1);
        }
    }

    public double getQ() {
        return q;
    }
}
