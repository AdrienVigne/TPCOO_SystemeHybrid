package fr.tp.coo;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Block implements ModeleAtomique{
    ArrayList<Entree> listeEntree;
    ArrayList<Sortie> listeSortie;
    HashMap<String,Etat> ensembleEtat;


}
