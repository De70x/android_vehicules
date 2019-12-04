package fr.eni.lokacar.dao.vehicule;

public class VehiculeContract {

/*
    private int id;
    private Marque marque;
    private Modele modele;
    private String immatriculation;
    private float prixParJour;
    private List<String> photos;
    private boolean isLouee;
 */

    public static final String TABLE_NAME = "vehicule";
    public static final String COL_ID = "id";
    public static final String COL_MODELE = "modele";
    public static final String COL_IMMATRICULATION = "immatriculation";
    public static final String COL_PRIX = "prix";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ( " +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_MODELE + " INTEGER," +
            COL_IMMATRICULATION + " TEXT," +
            COL_PRIX + " REAL" +
            ");";

    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
}
