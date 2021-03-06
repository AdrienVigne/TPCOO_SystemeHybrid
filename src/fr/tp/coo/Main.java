package fr.tp.coo;


import chart.Chart;
import chart.ChartFrame;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
//        GBF();
//        ensembleStepperAdder();
//        testStepper();
//        ensembleStepper();
//        integrateurTempsDiscret();
//        integrateurEventDiscret();
//        comparaissonIntegrateur();
//        ode2();
        bouncingball();
    }
    public static void bouncingball(){
        Constante g = new Constante(-9.81);
        IntegrateurEventDiscret inte1 = new IntegrateurEventDiscret(0.1);
        IntegrateurEventDiscret inte2 = new IntegrateurEventDiscret(0.1);
        inte2.val = 10;
        g.constante.ajoutObservateur(inte1.pval);
        inte1.res.ajoutObservateur(inte2.pval);

        double tfin = 3;
        ArrayList<Block> listeComposant = new ArrayList<>();
        listeComposant.add(g);
        listeComposant.add(inte1);
        listeComposant.add(inte2);

        ordonnanceurBoucingBall(listeComposant,tfin);

    }
    public static void ordonnanceurBoucingBall(ArrayList<Block> listeComposant, double tfin) {
        ChartFrame frame = new ChartFrame("EnsembleStepper + adder + iintegrateur","4 Steppper +add +int");
        Chart C = new Chart("Const");
        Chart int1 = new Chart("inte1");
        Chart int2 = new Chart("inte2");


        frame.addToLineChartPane(C);
        frame.addToLineChartPane(int1);
        frame.addToLineChartPane(int2);
        ArrayList<Chart> listChart = new ArrayList<>();
        listChart.add(C);
        listChart.add(int1);
        listChart.add(int2);

        double t = 0;
        double h = 0;
        listeComposant.forEach(block -> block.init());
        listeComposant.forEach(comp -> comp.setTr(comp.avancement()));
        while (t < tfin) {
            C.addDataToSeries(t,((Constante)listeComposant.get(0)).val);
            int1.addDataToSeries(t,((IntegrateurEventDiscret)listeComposant.get(1)).val);
            h = ((IntegrateurEventDiscret)listeComposant.get(2)).val;
            int2.addDataToSeries(t,h);

            if(h<=0 && ((IntegrateurEventDiscret)listeComposant.get(1)).val <=0){
                ((IntegrateurEventDiscret)listeComposant.get(1)).val = ((IntegrateurEventDiscret)listeComposant.get(1)).val * -0.5;
            }
//            int i = 0;
//            for (Chart c : listChart) {
//                c.addDataToSeries(t,);
//            }
//                if(i<=0){
//                    c.addDataToSeries(t,((Step)listeComposant.get(i)).valeurCourante);
//                }else if(i==4) {
//                    c.addDataToSeries(t,((adder4I)listeComposant.get(i)).res);
//                }else if(i==5){
//                    c.addDataToSeries(t,((IntegrateurEventDiscret)listeComposant.get(i)).val);
//                }else if(i==6){
//                    c.addDataToSeries(t,((IntegrateurTempsDiscret)listeComposant.get(i)).val);
//                }
//
//                i++;
//            }
//            s1.addDataToSeries(t,((Step)listeComposant.get(0)).valeurCourante);
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
            // pour lanc?? une seul fois le code pendant le dev
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

    public static void ode2(){
        Constante g = new Constante(-9.81);
        IntegrateurEventDiscret inte1 = new IntegrateurEventDiscret(0.1);
        IntegrateurEventDiscret inte2 = new IntegrateurEventDiscret(0.1);
        inte2.val = 10;
        g.constante.ajoutObservateur(inte1.pval);
        inte1.res.ajoutObservateur(inte2.pval);

        double tfin = 3;
        ArrayList<Block> listeComposant = new ArrayList<>();
        listeComposant.add(g);
        listeComposant.add(inte1);
        listeComposant.add(inte2);

        ordonnanceurOde2(listeComposant,tfin);

    }
    public static void ordonnanceurOde2(ArrayList<Block> listeComposant, double tfin) {
        ChartFrame frame = new ChartFrame("EnsembleStepper + adder + iintegrateur","4 Steppper +add +int");
        Chart C = new Chart("Const");
        Chart int1 = new Chart("inte1");
        Chart int2 = new Chart("inte2");


        frame.addToLineChartPane(C);
        frame.addToLineChartPane(int1);
        frame.addToLineChartPane(int2);
        ArrayList<Chart> listChart = new ArrayList<>();
        listChart.add(C);
        listChart.add(int1);
        listChart.add(int2);

        double t = 0;
        listeComposant.forEach(block -> block.init());
        listeComposant.forEach(comp -> comp.setTr(comp.avancement()));
        while (t < tfin) {
            C.addDataToSeries(t,((Constante)listeComposant.get(0)).val);
            int1.addDataToSeries(t,((IntegrateurEventDiscret)listeComposant.get(1)).val);
            int2.addDataToSeries(t,((IntegrateurEventDiscret)listeComposant.get(2)).val);
//            int i = 0;
//            for (Chart c : listChart) {
//                c.addDataToSeries(t,);
//            }
//                if(i<=0){
//                    c.addDataToSeries(t,((Step)listeComposant.get(i)).valeurCourante);
//                }else if(i==4) {
//                    c.addDataToSeries(t,((adder4I)listeComposant.get(i)).res);
//                }else if(i==5){
//                    c.addDataToSeries(t,((IntegrateurEventDiscret)listeComposant.get(i)).val);
//                }else if(i==6){
//                    c.addDataToSeries(t,((IntegrateurTempsDiscret)listeComposant.get(i)).val);
//                }
//
//                i++;
//            }
//            s1.addDataToSeries(t,((Step)listeComposant.get(0)).valeurCourante);
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
            // pour lanc?? une seul fois le code pendant le dev
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

    public static void comparaissonIntegrateur(){
        Step s1 = new Step(1,-3,0.65);
        s1.setNom("s1");
        Step s2 = new Step(0,1,0.35);
        s2.setNom("s2");
        Step s3 = new Step(0,1,1);
        s3.setNom("s3");
        Step s4 = new Step(0,4,1.5);
        s4.setNom("s4");
        adder4I add4 = new adder4I();
        IntegrateurEventDiscret inteEvent = new IntegrateurEventDiscret(0.1);
        IntegrateurTempsDiscret inteTemps = new IntegrateurTempsDiscret(0.1);


        s1.val.ajoutObservateur(add4.e1);
        s2.val.ajoutObservateur(add4.e2);
        s3.val.ajoutObservateur(add4.e3);
        s4.val.ajoutObservateur(add4.e4);
        add4.add.ajoutObservateur(inteEvent.pval);
        add4.add.ajoutObservateur(inteTemps.pval);



//        System.out.println(s1);
        double tfin = 3;
        ArrayList<Block> listeComposant = new ArrayList<>();
        listeComposant.add(s1);
        listeComposant.add(s2);
        listeComposant.add(s3);
        listeComposant.add(s4);
        listeComposant.add(add4);
        listeComposant.add(inteEvent);
        listeComposant.add(inteTemps);

        ordonnanceurCompareInte(listeComposant,tfin);

    }
    public static void ordonnanceurCompareInte(ArrayList<Block> listeComposant, double tfin) {
        ChartFrame frame = new ChartFrame("EnsembleStepper + adder + iintegrateur","4 Steppper +add +int");
        Chart s1 = new Chart("Step1");
        Chart s2 = new Chart("Step2");
        Chart s3 = new Chart("Step3");
        Chart s4 = new Chart("Step4");
        Chart adder = new Chart("Adder");
        Chart inteTemps = new Chart("IntegrateurTemps");
        Chart inteEvent = new Chart("IntegrateurEvent");


        frame.addToLineChartPane(s1);
        frame.addToLineChartPane(s2);
        frame.addToLineChartPane(s3);
        frame.addToLineChartPane(s4);
        frame.addToLineChartPane(adder);
        frame.addToLineChartPane(inteTemps);
        frame.addToLineChartPane(inteEvent);
        ArrayList<Chart> listChart = new ArrayList<>();
        listChart.add(s1);
        listChart.add(s2);
        listChart.add(s3);
        listChart.add(s4);
        listChart.add(adder);
        listChart.add(inteTemps);
        listChart.add(inteEvent);

        double t = 0;
        listeComposant.forEach(block -> block.init());
        listeComposant.forEach(comp -> comp.setTr(comp.avancement()));
        while (t < tfin) {
            int i = 0;
            for (Chart c : listChart){
                if(i<=3){
                    c.addDataToSeries(t,((Step)listeComposant.get(i)).valeurCourante);
                }else if(i==4) {
                    c.addDataToSeries(t,((adder4I)listeComposant.get(i)).res);
                }else if(i==5){
                    c.addDataToSeries(t,((IntegrateurEventDiscret)listeComposant.get(i)).val);
                }else if(i==6){
                    c.addDataToSeries(t,((IntegrateurTempsDiscret)listeComposant.get(i)).val);
                }

                i++;
            }
//            s1.addDataToSeries(t,((Step)listeComposant.get(0)).valeurCourante);
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
            // pour lanc?? une seul fois le code pendant le dev
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


    public static void integrateurEventDiscret(){
        Step s1 = new Step(1,-3,0.65);
        s1.setNom("s1");
        Step s2 = new Step(0,1,0.35);
        s2.setNom("s2");
        Step s3 = new Step(0,1,1);
        s3.setNom("s3");
        Step s4 = new Step(0,4,1.5);
        s4.setNom("s4");
        adder4I add4 = new adder4I();
        IntegrateurEventDiscret inte = new IntegrateurEventDiscret(0.1);

        s1.val.ajoutObservateur(add4.e1);
        s2.val.ajoutObservateur(add4.e2);
        s3.val.ajoutObservateur(add4.e3);
        s4.val.ajoutObservateur(add4.e4);
        add4.add.ajoutObservateur(inte.pval);



//        System.out.println(s1);
        double tfin = 3;
        ArrayList<Block> listeComposant = new ArrayList<>();
        listeComposant.add(s1);
        listeComposant.add(s2);
        listeComposant.add(s3);
        listeComposant.add(s4);
        listeComposant.add(add4);
        listeComposant.add(inte);

        ordonnanceurIntEventDiscret(listeComposant,tfin);


    }
    public static void ordonnanceurIntEventDiscret(ArrayList<Block> listeComposant, double tfin) {
        ChartFrame frame = new ChartFrame("EnsembleStepper + adder + iintegrateur","4 Steppper +add +int");
        Chart s1 = new Chart("Step1");
        Chart s2 = new Chart("Step2");
        Chart s3 = new Chart("Step3");
        Chart s4 = new Chart("Step4");
        Chart adder = new Chart("Adder");
        Chart integ = new Chart("Integrateur");

        frame.addToLineChartPane(s1);
        frame.addToLineChartPane(s2);
        frame.addToLineChartPane(s3);
        frame.addToLineChartPane(s4);
        frame.addToLineChartPane(adder);
        frame.addToLineChartPane(integ);
        ArrayList<Chart> listChart = new ArrayList<>();
        listChart.add(s1);
        listChart.add(s2);
        listChart.add(s3);
        listChart.add(s4);
        listChart.add(adder);
        listChart.add(integ);

        double t = 0;
        listeComposant.forEach(block -> block.init());
        listeComposant.forEach(comp -> comp.setTr(comp.avancement()));
        while (t < tfin) {
            int i = 0;
            for (Chart c : listChart){
                if(i<=3){
                    c.addDataToSeries(t,((Step)listeComposant.get(i)).valeurCourante);
                }else if(i==4) {
                    c.addDataToSeries(t,((adder4I)listeComposant.get(i)).res);
                }else {
                    c.addDataToSeries(t,((IntegrateurEventDiscret)listeComposant.get(i)).val);
                }

                i++;
            }
//            s1.addDataToSeries(t,((Step)listeComposant.get(0)).valeurCourante);
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
            // pour lanc?? une seul fois le code pendant le dev
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
    public static void integrateurTempsDiscret(){
        Step s1 = new Step(1,-3,0.65);
        s1.setNom("s1");
        Step s2 = new Step(0,1,0.35);
        s2.setNom("s2");
        Step s3 = new Step(0,1,1);
        s3.setNom("s3");
        Step s4 = new Step(0,4,1.5);
        s4.setNom("s4");
        adder4I add4 = new adder4I();
        IntegrateurTempsDiscret inte = new IntegrateurTempsDiscret(0.01);

        s1.val.ajoutObservateur(add4.e1);
        s2.val.ajoutObservateur(add4.e2);
        s3.val.ajoutObservateur(add4.e3);
        s4.val.ajoutObservateur(add4.e4);
        add4.add.ajoutObservateur(inte.pval);



//        System.out.println(s1);
        double tfin = 3;
        ArrayList<Block> listeComposant = new ArrayList<>();
        listeComposant.add(s1);
        listeComposant.add(s2);
        listeComposant.add(s3);
        listeComposant.add(s4);
        listeComposant.add(add4);
        listeComposant.add(inte);

        ordonnanceurIntTempsDiscret(listeComposant,tfin);


    }
    public static void ordonnanceurIntTempsDiscret(ArrayList<Block> listeComposant, double tfin) {
        ChartFrame frame = new ChartFrame("EnsembleStepper + adder + iintegrateur","4 Steppper +add +int");
        Chart s1 = new Chart("Step1");
        Chart s2 = new Chart("Step2");
        Chart s3 = new Chart("Step3");
        Chart s4 = new Chart("Step4");
        Chart adder = new Chart("Adder");
        Chart integ = new Chart("Integrateur");

        frame.addToLineChartPane(s1);
        frame.addToLineChartPane(s2);
        frame.addToLineChartPane(s3);
        frame.addToLineChartPane(s4);
        frame.addToLineChartPane(adder);
        frame.addToLineChartPane(integ);
        ArrayList<Chart> listChart = new ArrayList<>();
        listChart.add(s1);
        listChart.add(s2);
        listChart.add(s3);
        listChart.add(s4);
        listChart.add(adder);
        listChart.add(integ);

        double t = 0;
        listeComposant.forEach(block -> block.init());
        listeComposant.forEach(comp -> comp.setTr(comp.avancement()));
        while (t < tfin) {
            int i = 0;
            for (Chart c : listChart){
                if(i<=3){
                    c.addDataToSeries(t,((Step)listeComposant.get(i)).valeurCourante);
                }else if(i==4) {
                    c.addDataToSeries(t,((adder4I)listeComposant.get(i)).res);
                }else {
                    c.addDataToSeries(t,((IntegrateurTempsDiscret)listeComposant.get(i)).val);
                }

                i++;
            }
//            s1.addDataToSeries(t,((Step)listeComposant.get(0)).valeurCourante);
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
            // pour lanc?? une seul fois le code pendant le dev
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

        ordonnanceurEnsembleStepper(listeComposant,tfin);


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
            q.addDataToSeries(t,((Step)listeComposant.get(0)).valeurCourante);
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
            System.out.println(listeComposantImms);
            t = t + tr_min;

            System.out.println("temps : " + t + " tr_min "+tr_min);

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
            // pour lanc?? une seul fois le code pendant le dev
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
                    c.addDataToSeries(t,((Step)listeComposant.get(i)).valeurCourante);
                }else {
                    c.addDataToSeries(t,((adder4I)listeComposant.get(i)).res);
                }

                i++;
            }
//            s1.addDataToSeries(t,((Step)listeComposant.get(0)).valeurCourante);
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
            // pour lanc?? une seul fois le code pendant le dev
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
                c.addDataToSeries(t,((Step)listeComposant.get(i)).valeurCourante);
                i++;
            }
//            s1.addDataToSeries(t,((Step)listeComposant.get(0)).valeurCourante);
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
            // pour lanc?? une seul fois le code pendant le dev
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
            // pour lanc?? une seul fois le code pendant le dev
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
