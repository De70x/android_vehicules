package fr.eni.lokacar.bo.vehicule.enumvehicule;

public enum Marque {
    //Objets directement construits
    PEUGEOT("Peugeot", 0),
    RENAULT("Renault", 1),
    OPEL("Opel", 2),
    CITROEN("Citroën", 3);

/*                  Volkswagen.
                    Mercedes.
                    Nissan.
                    Audi.*/

    private String nom = null;
    private int id = 0;

    //Constructeur
    Marque(String nom, int numero){
        this.nom = nom;
        this.id = numero;
    }

    public int getId(){
        return id;
    }

    public String toString(){
        return nom;
    }

}
