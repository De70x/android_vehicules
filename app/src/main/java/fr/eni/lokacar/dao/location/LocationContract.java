package fr.eni.lokacar.dao.location;

import fr.eni.lokacar.dao.client.ClientContract;
import fr.eni.lokacar.dao.vehicule.VehiculeContract;

public class LocationContract {

    public static final String TABLE_NAME = "location";
    public static final String COL_ID = "id";
    public static final String COL_VEHICULE_ID = "vehicule_id";
    public static final String COL_CLIENT_ID = "prenom_id";
    public static final String COL_DEBUT = "debut";
    public static final String COL_FIN = "fin";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ( " +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_VEHICULE_ID + " INTEGER," +
            COL_CLIENT_ID + " INTEGER," +
            COL_DEBUT + " TEXT," +
            COL_FIN + " TEXT," +
            "FOREIGN KEY(" + COL_VEHICULE_ID + ") REFERENCES " + VehiculeContract.TABLE_NAME + "("+ VehiculeContract.COL_ID  +")," +
            "FOREIGN KEY(" + COL_CLIENT_ID + ") REFERENCES " + ClientContract.TABLE_NAME + "("+ ClientContract.COL_ID  +")" +
            ");";

    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
}


