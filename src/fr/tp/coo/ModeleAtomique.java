package fr.tp.coo;

public interface ModeleAtomique {
    void externe();

    void interne();

    double avancement();

    void sortie();

    void init();

    void conflict();

    void finCycle();

}

