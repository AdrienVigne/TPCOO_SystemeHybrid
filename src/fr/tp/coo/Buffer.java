package fr.tp.coo;

import chart.Chart;
import chart.ChartFrame;

public class Buffer extends Block {
    Etat a, b, c;
    Sortie req;
    Entree job, done;
    double q = 0;
    ChartFrame frame;
    Chart Q;

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

        frame = new ChartFrame("toto","tata");
        Q = new Chart("Q");
        frame.addToLineChartPane(Q);
        Q.addDataToSeries(0,q);
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
            Q.addDataToSeries(tl,q);
        }
        if (etat_courant == c) {
             if (done.getFlag()) {
                if (q == 0) {
                    etat_courant = a;
                } else {
                    etat_courant = b;
                }
            }else if (job.getFlag()) {
                q++;

                Q.addDataToSeries(tl,q);
            }

        }
    }

    @Override
    public void interne() {
        if (etat_courant == b) {
            q--;
            etat_courant = c;

            Q.addDataToSeries(tl,q);
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
