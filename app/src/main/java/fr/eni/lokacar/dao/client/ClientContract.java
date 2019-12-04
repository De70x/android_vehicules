package fr.eni.lokacar.dao.client;

public class ClientContract {

    public static final String TABLE_NAME = "client";
    public static final String COL_ID = "id";
    public static final String COL_NOM = "nom";
    public static final String COL_PRENOM = "prenom";
    public static final String COL_MAIL = "mail";
    public static final String COL_ADRESSE = "adresse";
    public static final String COL_TELEPHONE = "telephone";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ( " +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_NOM + " TEXT," +
            COL_PRENOM + " TEXT," +
            COL_MAIL + " TEXT," +
            COL_ADRESSE + " TEXT," +
            COL_TELEPHONE + " TEXT" +
            ");";

    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
}
