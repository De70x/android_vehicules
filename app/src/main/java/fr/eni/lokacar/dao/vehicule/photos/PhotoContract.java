package fr.eni.lokacar.dao.vehicule.photos;

import fr.eni.lokacar.dao.vehicule.VehiculeContract;

public class PhotoContract {

    public static final String TABLE_NAME = "photo";
    public static final String COL_ID = "id";
    public static final String COL_VEHICULE = "vehicule";
    public static final String COL_PHOTO = "photo";
    public static final String COL_PATH = "path";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ( " +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_VEHICULE + " INTEGER," +
            COL_PHOTO + " BLOB," +
            COL_PATH + " TEXT," +
            "FOREIGN KEY(" + COL_VEHICULE + ") REFERENCES " + VehiculeContract.TABLE_NAME + "("+ VehiculeContract.COL_ID  +")" +
            ");";

    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
}
