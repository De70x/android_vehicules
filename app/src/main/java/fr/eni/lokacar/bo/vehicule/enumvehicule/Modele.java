package fr.eni.lokacar.bo.vehicule.enumvehicule;

import java.util.List;

public enum Modele {
    CLIO("Clio", Marque.RENAULT, 0),
    CAPTUR("Captur", Marque.RENAULT, 1),
    P208("208", Marque.PEUGEOT, 2),
    P3008("3008", Marque.PEUGEOT, 3);

    private String nom = null;
    private Marque marque = null;
    private int id = 0;

    //Constructeur
    Modele(String nom, Marque marque, int id){
        this.nom = nom;
        this.marque = marque;
        this.id = id;
    }

    public int getId(){ return id; }

    public Marque getMarque(){
        return marque;
    }

    public String toString(){
        return nom;
    }
}
