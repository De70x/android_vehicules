package fr.eni.lokacar.dao.vehicule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.bo.vehicule.Vehicule;
import fr.eni.lokacar.bo.vehicule.enumvehicule.Modele;
import fr.eni.lokacar.dao.general.BDDHelper;

public class VehiculeDAO {

    private SQLiteDatabase db;
    private BDDHelper bddHelper;

    public VehiculeDAO(Context context)
    {
        bddHelper = new BDDHelper(context);
        db = bddHelper.getWritableDatabase();
    }

    public long insert(Vehicule vehicule)
    {
        ContentValues values = new ContentValues();

        values.put(VehiculeContract.COL_IMMATRICULATION, vehicule.getImmatriculation());
        values.put(VehiculeContract.COL_MODELE, vehicule.getModele().getId());
        values.put(VehiculeContract.COL_PRIX, vehicule.getPrixParJour());

        return db.insert(VehiculeContract.TABLE_NAME,null,values);
    }

    public boolean update(Vehicule vehicule)
    {
        ContentValues values = new ContentValues();

        values.put(VehiculeContract.COL_IMMATRICULATION, vehicule.getImmatriculation());
        values.put(VehiculeContract.COL_MODELE, vehicule.getModele().getId());
        values.put(VehiculeContract.COL_PRIX, vehicule.getPrixParJour());

        return db.update(VehiculeContract.TABLE_NAME, values,VehiculeContract.COL_ID + "=?",new
                String[]{String.valueOf(vehicule.getId())})>0;
    }

    public Vehicule get(long id){
        Vehicule vehicule = null;
        Cursor cursor = db.query(
                VehiculeContract.TABLE_NAME,
                new String[]{VehiculeContract.COL_ID,VehiculeContract.COL_IMMATRICULATION, VehiculeContract.COL_MODELE, VehiculeContract.COL_PRIX},
                VehiculeContract.COL_ID + " =?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if(cursor.moveToNext())
        {
            vehicule = new Vehicule();
            vehicule.setId(cursor.getInt(cursor.getColumnIndex(VehiculeContract.COL_ID)));
            vehicule.setImmatriculation(cursor.getString(cursor.getColumnIndex(VehiculeContract.COL_IMMATRICULATION)));
            vehicule.setPrixParJour(cursor.getFloat(cursor.getColumnIndex(VehiculeContract.COL_PRIX)));
            Modele modele = Modele.values()[cursor.getInt(cursor.getColumnIndex(VehiculeContract.COL_MODELE))];
            vehicule.setModele(modele);
        }
        return vehicule;
    }


    public List<Vehicule> getAll()
    {
        List<Vehicule> resultat = new ArrayList<>();
        Cursor cursor = db.query(
                VehiculeContract.TABLE_NAME,
                new String[]{VehiculeContract.COL_ID,VehiculeContract.COL_IMMATRICULATION, VehiculeContract.COL_MODELE, VehiculeContract.COL_PRIX},
                null,
                null,
                null,
                null,
                null);
        while(cursor.moveToNext())
        {
            Vehicule vehicule = new Vehicule();
            vehicule.setId(cursor.getInt(cursor.getColumnIndex(VehiculeContract.COL_ID)));
            vehicule.setImmatriculation(cursor.getString(cursor.getColumnIndex(VehiculeContract.COL_IMMATRICULATION)));
            Modele modele = Modele.values()[cursor.getInt(cursor.getColumnIndex(VehiculeContract.COL_MODELE))];
            vehicule.setModele(modele);
            vehicule.setPrixParJour(cursor.getFloat(cursor.getColumnIndex(VehiculeContract.COL_PRIX)));

            resultat.add(vehicule);
        }
        return resultat;
    }
}
