package fr.tp.coo;


import chart.Chart;
import chart.ChartFrame;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        // write your code here

//        GBF();
        ensembleStepperAdder();
//        testStepper();
        // System.out.println("Index : "+minIndex+" composant associé : "+ listeComposant.get(minIndex).nom +" tr_min : "+tr_min);
    }
    public static void testStepper(){
        Step s1 = new Step(1,-3,0.65);
        s1.setNom("s1");
        double tfin = 3;
        ArrayList<Block> listeComposant = new ArrayList<>();
        listeComposant.add(s1);
        ordonnanceur_test_stepper(listeComposant,tfin);

    }
    public static void ensembleStepperAdder(){
        Step s1 = new Step(1,-3,0.65);
        s1.setNom("s1");
        Step s2 = new Step(0,1,0.35);
        s2.setNom("s2");
        Step s3 = new Step(0,1,1);
        s3.setNom("s3");
        Step s4 = new Step(0,4,1.5);
        s4.setNom("s4");
        adder4I add4 = new adder4I();

        s1.val.ajoutObservateur(add4.e1);
        s2.val.ajoutObservateur(add4.e2);
        s3.val.ajoutObservateur(add4.e3);
        s4.val.ajoutObservateur(add4.e4);



//        System.out.println(s1);
        double tfin = 3;
        ArrayList<Block> listeComposant = new ArrayList<>();
        listeComposant.add(s1);
        listeComposant.add(s2);
        listeComposant.add(s3);
        listeComposant.add(s4);
        listeComposant.add(add4);

        ordonnanceurEnsembleStepperAdder(listeComposant,tfin);


    }



    public static void ensembleStepper(){
        Step s1 = new Step(1,-3,0.65);
        s1.setNom("s1");
        Step s2 = new Step(0,1,0.35);
        s2.setNom("s2");
        Step s3 = new Step(0,1,1);
        s3.setNom("s3");
        Step s4 = new Step(0,4,1.5);
        s4.setNom("s4");
//        System.out.println(s1);
        double tfin = 3;
        ArrayList<Block> listeComposant = new ArrayList<>();
        listeComposant.add(s1);
        listeComposant.add(s2);
        listeComposant.add(s3);
        listeComposant.add(s4);

        ordonnanceurEnsembleStepperAdder(listeComposant,tfin);


    }

    public static void GBF() {

        Buffer B = new Buffer();
        Generateur G = new Generateur(2);
        Processeur P = new Processeur(3);
        G.job.ajoutObservateur(B.job);
        P.done.ajoutObservateur(B.done);
        B.req.ajoutObservateur(P.req);

        double t = 0;
        double tfin = 20;

        ArrayList<Block> listeComposant = new ArrayList<>();
        listeComposant.add(B);
        listeComposant.add(G);
        listeComposant.add(P);
//        System.out.println(listeComposant);

        ordonnanceur_GBF(listeComposant, tfin);

    }

    public static void ordonnanceur_test_stepper(ArrayList<Block> listeComposant, double tfin) {
        ChartFrame frame = new ChartFrame("toto","titre");
        Chart q = new Chart("varadder1");
        frame.addToLineChartPane(q);
        double t = 0;
        listeComposant.forEach(block -> block.init());
        listeComposant.forEach(comp -> comp.setTr(comp.avancement()));
        while (t < tfin) {
            q.addDataToSeries(t,((Step)listeComposant.get(0)).valeur_courante);
            ArrayList<Block> listeComposantImms = new ArrayList<>();
            ArrayList<Double> listeTr = new ArrayList<>();
            double tr_min = 0;
            listeComposant.forEach(comp -> listeTr.add(comp.getTr()));
            //System.out.println(listeTr);
            tr_min = Collections.min(listeTr);
//            int minIndex = listeTr.indexOf(tr_min);

            for (Block comp : listeComposant)
                if (tr_min == comp.getTr())
                    listeComposantImms.add(comp);
//            System.out.println(listeComposantImms);
            t = t + tr_min;
            System.out.println("temps : " + t + "tr_min "+tr_min);
//            for (Block comp : listeComposant) {
//                comp.listeSortie.forEach(sortie -> System.out.println(sortie.valeur));
//                System.out.println(comp.getE());
//                System.out.println(comp.getTr());
//            }
            for (Block comp : listeComposant) {
                comp.setE(comp.getE() + tr_min);
                comp.setTr(comp.getTr() - tr_min);
            }
//            for (Block comp : listeComposant) {
//                System.out.println(comp);
//                System.out.println(comp.getE());
//                System.out.println(comp.getTr());
//            }
            // pour lancé une seul fois le code pendant le dev
            for (Block comp : listeComposantImms)
                comp.sortie();

//            for (Block comp : listeComposant) {
//                System.out.println(comp);
//                comp.listeEntree.forEach(entree -> {
//                    System.out.println(entree.getNom() + " : " + entree.getFlag());
//                });
//            }

            ArrayList<Entree> listeEntreeIns = new ArrayList<>();
            for (Block comp : listeComposant) {
                for (Entree e : comp.listeEntree) {
                    if (e.getFlag()) {
                        listeEntreeIns.add(e);
                    }
                }
            }
            System.out.println(listeEntreeIns);


            for (Block comp : listeComposant) {
                if (listeComposantImms.contains(comp) && !comp.entreeImpactee()) {
//                    System.out.println("comp : "+comp);
                    comp.interne();
                    comp.setTl(t);
                    comp.setE(0);
                    comp.setTr(comp.avancement());
                    comp.setTn(t + comp.getTr());

                }
                if (!listeComposantImms.contains(comp) && comp.entreeImpactee()) {
                    comp.externe();
                    comp.setTl(t);
                    comp.setE(0);
                    comp.setTr(comp.avancement());
                    comp.setTn(t + comp.getTr());
                }
                if (listeComposantImms.contains(comp) && comp.entreeImpactee()) {
                    comp.conflict();
                    comp.setTl(t);
                    comp.setE(0);
                    comp.setTr(comp.avancement());
                    comp.setTn(t + comp.getTr());
                }
            }
            for (Block comp : listeComposant) {
                comp.finCycle();
            }
        }
    }
    public static void ordonnanceurEnsembleStepperAdder(ArrayList<Block> listeComposant, double tfin) {
        ChartFrame frame = new ChartFrame("EnsembleStepper","4 Steppper");
        Chart s1 = new Chart("Step1");
        Chart s2 = new Chart("Step2");
        Chart s3 = new Chart("Step3");
        Chart s4 = new Chart("Step4");
        Chart adder = new Chart("Adder");
        frame.addToLineChartPane(s1);
        frame.addToLineChartPane(s2);
        frame.addToLineChartPane(s3);
        frame.addToLineChartPane(s4);
        frame.addToLineChartPane(adder);
        ArrayList<Chart> listChart = new ArrayList<>();
        listChart.add(s1);
        listChart.add(s2);
        listChart.add(s3);
        listChart.add(s4);
        listChart.add(adder);

        double t = 0;
        listeComposant.forEach(block -> block.init());
        listeComposant.forEach(comp -> comp.setTr(comp.avancement()));
        while (t < tfin) {
            int i = 0;
            for (Chart c : listChart){
                if(i<=3){
                    c.addDataToSeries(t,((Step)listeComposant.get(i)).valeur_courante);
                }else {
                    c.addDataToSeries(t,((adder4I)listeComposant.get(i)).res);
                }

                i++;
            }
//            s1.addDataToSeries(t,((Step)listeComposant.get(0)).valeur_courante);
            ArrayList<Block> listeComposantImms = new ArrayList<>();
            ArrayList<Double> listeTr = new ArrayList<>();
            double tr_min = 0;
            listeComposant.forEach(comp -> listeTr.add(comp.getTr()));
            //System.out.println(listeTr);
            tr_min = Collections.min(listeTr);
//            int minIndex = listeTr.indexOf(tr_min);

            for (Block comp : listeComposant)
                if (tr_min == comp.getTr())
                    listeComposantImms.add(comp);
//            System.out.println(listeComposantImms);
            t = t + tr_min;
            System.out.println("temps : " + t + "tr_min "+tr_min);
//            for (Block comp : listeComposant) {
//                comp.listeSortie.forEach(sortie -> System.out.println(sortie.valeur));
//                System.out.println(comp.getE());
//                System.out.println(comp.getTr());
//            }
            for (Block comp : listeComposant) {
                comp.setE(comp.getE() + tr_min);
                comp.setTr(comp.getTr() - tr_min);
            }
//            for (Block comp : listeComposant) {
//                System.out.println(comp);
//                System.out.println(comp.getE());
//                System.out.println(comp.getTr());
//            }
            // pour lancé une seul fois le code pendant le dev
            for (Block comp : listeComposantImms)
                comp.sortie();

//            for (Block comp : listeComposant) {
//                System.out.println(comp);
//                comp.listeEntree.forEach(entree -> {
//                    System.out.println(entree.getNom() + " : " + entree.getFlag());
//                });
//            }

            ArrayList<Entree> listeEntreeIns = new ArrayList<>();
            for (Block comp : listeComposant) {
                for (Entree e : comp.listeEntree) {
                    if (e.getFlag()) {
                        listeEntreeIns.add(e);
                    }
                }
            }
            System.out.println(listeEntreeIns);


            for (Block comp : listeComposant) {
                if (listeComposantImms.contains(comp) && !comp.entreeImpactee()) {
//                    System.out.println("comp : "+comp);
                    comp.interne();
                    comp.setTl(t);
                    comp.setE(0);
                    comp.setTr(comp.avancement());
                    comp.setTn(t + comp.getTr());

                }
                if (!listeComposantImms.contains(comp) && comp.entreeImpactee()) {
                    comp.externe();
                    comp.setTl(t);
                    comp.setE(0);
                    comp.setTr(comp.avancement());
                    comp.setTn(t + comp.getTr());
                }
                if (listeComposantImms.contains(comp) && comp.entreeImpactee()) {
                    comp.conflict();
                    comp.setTl(t);
                    comp.setE(0);
                    comp.setTr(comp.avancement());
                    comp.setTn(t + comp.getTr());
                }
            }
            for (Block comp : listeComposant) {
                comp.finCycle();
            }
        }
    }

    public static void ordonnanceurEnsembleStepper(ArrayList<Block> listeComposant, double tfin) {
        ChartFrame frame = new ChartFrame("EnsembleStepper","4 Steppper");
        Chart s1 = new Chart("Step1");
        Chart s2 = new Chart("Step2");
        Chart s3 = new Chart("Step3");
        Chart s4 = new Chart("Step4");
        frame.addToLineChartPane(s1);
        frame.addToLineChartPane(s2);
        frame.addToLineChartPane(s3);
        frame.addToLineChartPane(s4);
        ArrayList<Chart> listChart = new ArrayList<>();
        listChart.add(s1);
        listChart.add(s2);
        listChart.add(s3);
        listChart.add(s4);

        double t = 0;
        listeComposant.forEach(block -> block.init());
        listeComposant.forEach(comp -> comp.setTr(comp.avancement()));
        while (t < tfin) {
            int i = 0;
            for (Chart c : listChart){
                c.addDataToSeries(t,((Step)listeComposant.get(i)).valeur_courante);
                i++;
            }
//            s1.addDataToSeries(t,((Step)listeComposant.get(0)).valeur_courante);
            ArrayList<Block> listeComposantImms = new ArrayList<>();
            ArrayList<Double> listeTr = new ArrayList<>();
            double tr_min = 0;
            listeComposant.forEach(comp -> listeTr.add(comp.getTr()));
            //System.out.println(listeTr);
            tr_min = Collections.min(listeTr);
//            int minIndex = listeTr.indexOf(tr_min);

            for (Block comp : listeComposant)
                if (tr_min == comp.getTr())
                    listeComposantImms.add(comp);
//            System.out.println(listeComposantImms);
            t = t + tr_min;
            System.out.println("temps : " + t + "tr_min "+tr_min);
//            for (Block comp : listeComposant) {
//                comp.listeSortie.forEach(sortie -> System.out.println(sortie.valeur));
//                System.out.println(comp.getE());
//                System.out.println(comp.getTr());
//            }
            for (Block comp : listeComposant) {
                comp.setE(comp.getE() + tr_min);
                comp.setTr(comp.getTr() - tr_min);
            }
//            for (Block comp : listeComposant) {
//                System.out.println(comp);
//                System.out.println(comp.getE());
//                System.out.println(comp.getTr());
//            }
            // pour lancé une seul fois le code pendant le dev
            for (Block comp : listeComposantImms)
                comp.sortie();

//            for (Block comp : listeComposant) {
//                System.out.println(comp);
//                comp.listeEntree.forEach(entree -> {
//                    System.out.println(entree.getNom() + " : " + entree.getFlag());
//                });
//            }

            ArrayList<Entree> listeEntreeIns = new ArrayList<>();
            for (Block comp : listeComposant) {
                for (Entree e : comp.listeEntree) {
                    if (e.getFlag()) {
                        listeEntreeIns.add(e);
                    }
                }
            }
            System.out.println(listeEntreeIns);


            for (Block comp : listeComposant) {
                if (listeComposantImms.contains(comp) && !comp.entreeImpactee()) {
//                    System.out.println("comp : "+comp);
                    comp.interne();
                    comp.setTl(t);
                    comp.setE(0);
                    comp.setTr(comp.avancement());
                    comp.setTn(t + comp.getTr());

                }
                if (!listeComposantImms.contains(comp) && comp.entreeImpactee()) {
                    comp.externe();
                    comp.setTl(t);
                    comp.setE(0);
                    comp.setTr(comp.avancement());
                    comp.setTn(t + comp.getTr());
                }
                if (listeComposantImms.contains(comp) && comp.entreeImpactee()) {
                    comp.conflict();
                    comp.setTl(t);
                    comp.setE(0);
                    comp.setTr(comp.avancement());
                    comp.setTn(t + comp.getTr());
                }
            }
            for (Block comp : listeComposant) {
                comp.finCycle();
            }
        }
    }

    public static void ordonnanceur_GBF(ArrayList<Block> listeComposant, double tfin) {
        ChartFrame frame = new ChartFrame("GBF","q");
        Chart q = new Chart("q");
        frame.addToLineChartPane(q);
        double t = 0;
        listeComposant.forEach(block -> block.init());
        listeComposant.forEach(comp -> comp.setTr(comp.avancement()));
        while (t < tfin) {
            q.addDataToSeries(t,((Buffer)listeComposant.get(0)).q);
            ArrayList<Block> listeComposantImms = new ArrayList<>();
            ArrayList<Double> listeTr = new ArrayList<>();
            double tr_min = 0;
            listeComposant.forEach(comp -> listeTr.add(comp.getTr()));
            //System.out.println(listeTr);
            tr_min = Collections.min(listeTr);
//            int minIndex = listeTr.indexOf(tr_min);

            for (Block comp : listeComposant)
                if (tr_min == comp.getTr())
                    listeComposantImms.add(comp);
//            System.out.println(listeComposantImms);
            t = t + tr_min;
            System.out.println("temps : " + t + "tr_min "+tr_min);
//            for (Block comp : listeComposant) {
//                comp.listeSortie.forEach(sortie -> System.out.println(sortie.valeur));
//                System.out.println(comp.getE());
//                System.out.println(comp.getTr());
//            }
            for (Block comp : listeComposant) {
                comp.setE(comp.getE() + tr_min);
                comp.setTr(comp.getTr() - tr_min);
            }
//            for (Block comp : listeComposant) {
//                System.out.println(comp);
//                System.out.println(comp.getE());
//                System.out.println(comp.getTr());
//            }
            // pour lancé une seul fois le code pendant le dev
            for (Block comp : listeComposantImms)
                comp.sortie();

//            for (Block comp : listeComposant) {
//                System.out.println(comp);
//                comp.listeEntree.forEach(entree -> {
//                    System.out.println(entree.getNom() + " : " + entree.getFlag());
//                });
//            }

            ArrayList<Entree> listeEntreeIns = new ArrayList<>();
            for (Block comp : listeComposant) {
                for (Entree e : comp.listeEntree) {
                    if (e.getFlag()) {
                        listeEntreeIns.add(e);
                    }
                }
            }
            System.out.println(listeEntreeIns);


            for (Block comp : listeComposant) {
                if (listeComposantImms.contains(comp) && !comp.entreeImpactee()) {
//                    System.out.println("comp : "+comp);
                    comp.interne();
                    comp.setTl(t);
                    comp.setE(0);
                    comp.setTr(comp.avancement());
                    comp.setTn(t + comp.getTr());

                }
                if (!listeComposantImms.contains(comp) && comp.entreeImpactee()) {
                    comp.externe();
                    comp.setTl(t);
                    comp.setE(0);
                    comp.setTr(comp.avancement());
                    comp.setTn(t + comp.getTr());
                }
                if (listeComposantImms.contains(comp) && comp.entreeImpactee()) {
                    comp.conflict();
                    comp.setTl(t);
                    comp.setE(0);
                    comp.setTr(comp.avancement());
                    comp.setTn(t + comp.getTr());
                }
            }
            for (Block comp : listeComposant) {
                comp.finCycle();
            }
        }
    }
}
