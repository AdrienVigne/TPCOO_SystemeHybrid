package fr.tp.coo;
import javax.swing.*;


import chart.*;
import org.jfree.data.general.Series;

public class Graph implements Observateur {
    ChartFrame frame;
    Chart q;
    public Graph(String nomFenetre,String titre){
        frame = new ChartFrame(nomFenetre,titre);
        q = new Chart("var");
        frame.addToLineChartPane(q);
        q.addDataToSeries(0,0);
    }


    @Override
    public void recevoirNotification(Observable obs, double val) {
//        System.out.println("COUCOU");
//         Series Ser = q.getSeries();
//         System.out.println(Ser);

    }

    @Override
    public void recevoirNotification(Observable obs, double val, double temps) {
        System.out.println("ajout var : "+val+" temps : "+temps);
        System.out.println();
        q.addDataToSeries(temps,val);
        System.out.println(q.getSeries());
    }
}
