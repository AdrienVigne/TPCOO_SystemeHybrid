package fr.tp.coo;

import java.util.ArrayList;

public interface Observable {

    void ajoutObservateur(Observateur obs);
    void supressionObservateur(Observateur obs);
    void notifierTous();

}
