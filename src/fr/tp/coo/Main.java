package fr.tp.coo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<Block> listeComposant = new ArrayList<>();
        double t = 0
        listeComposant.forEach(comp -> comp.init());
        listeComposant.forEach(comp -> comp.setTr(comp.avancement()));

        ArrayList<double> listeTr = new ArrayList<double>();
        double tr_min = 0;
        listeComposant.forEach(comp -> {listeTr.add(comp.getTr())});
    }
}
