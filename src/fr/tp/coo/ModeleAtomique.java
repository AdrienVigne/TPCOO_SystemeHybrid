package fr.tp.coo;

public interface ModeleAtomique {
    void externe(Sortie s);

    void interne(Entree e);

    double avancement();

    void sortie();

    void init();

}

