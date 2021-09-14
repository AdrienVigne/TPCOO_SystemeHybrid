package fr.tp.coo;

import java.util.ArrayList;

public interface Observable {
    ArrayList<Observateur> listeObservateur = new ArrayList<>();
    void ajoutObservateur(Observateur obs);
    void supressionObservateur(Observateur obs);
    void notifierTous();

}
