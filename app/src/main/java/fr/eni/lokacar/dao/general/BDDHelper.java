package fr.eni.lokacar.dao.general;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import fr.eni.lokacar.dao.client.ClientContract;
import fr.eni.lokacar.dao.location.LocationContract;
import fr.eni.lokacar.dao.vehicule.VehiculeContract;


public class BDDHelper extends SQLiteOpenHelper {

    private final static int VERSION = 2;
    private final static String BDD_NAME = "tp_article.db";
    private final static String TAG = "BDD";
    public BDDHelper(Context context)
    {
        super(context, BDD_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,"Passage dans le onCreate");
        db.execSQL(VehiculeContract.CREATE_TABLE);
        db.execSQL(ClientContract.CREATE_TABLE);
        db.execSQL(LocationContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"Passage dans le onUpgrade");
        db.execSQL(VehiculeContract.DROP_TABLE);
        db.execSQL(ClientContract.DROP_TABLE);
        db.execSQL(LocationContract.DROP_TABLE);
        onCreate(db);
    }
}
