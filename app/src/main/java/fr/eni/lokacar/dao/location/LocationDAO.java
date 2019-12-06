package fr.eni.lokacar.dao.location;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.lokacar.bo.client.Client;
import fr.eni.lokacar.bo.location.Location;
import fr.eni.lokacar.bo.vehicule.Vehicule;
import fr.eni.lokacar.dao.client.ClientDAO;
import fr.eni.lokacar.dao.general.BDDHelper;
import fr.eni.lokacar.dao.vehicule.VehiculeDAO;

public class LocationDAO {

    private SQLiteDatabase db;
    private BDDHelper bddHelper;
    private ClientDAO clientDAO;
    private VehiculeDAO vehiculeDAO;

    public LocationDAO(Context context)
    {
        bddHelper = new BDDHelper(context);
        db = bddHelper.getWritableDatabase();
        clientDAO = new ClientDAO(context);
        vehiculeDAO = new VehiculeDAO(context);
    }

    public long insert(Location location)
    {
        ContentValues values = new ContentValues();

        // On met à jour le véhicule
        Vehicule vehicule = location.getVehicule();

        vehiculeDAO.update(vehicule);

        values.put(LocationContract.COL_CLIENT_ID, location.getClient().getId());
        values.put(LocationContract.COL_VEHICULE_ID, location.getVehicule().getId());
        values.put(LocationContract.COL_DEBUT, location.getDateDebut().getTime());
        // S'il n'y a pas de location a venir
        Date dateFutureLocation = getLocationAVenir((long)vehicule.getId());
        if(dateFutureLocation == null)
        {
            values.put(LocationContract.COL_FIN, (String)null);
        }
        else
        {
            values.put(LocationContract.COL_FIN, dateFutureLocation.getTime());
        }

        return db.insert(LocationContract.TABLE_NAME,null,values);
    }

    public boolean update(Location location)
    {
        ContentValues values = new ContentValues();

        values.put(LocationContract.COL_ID, location.getId());
        values.put(LocationContract.COL_CLIENT_ID, location.getClient().getId());
        values.put(LocationContract.COL_VEHICULE_ID, location.getVehicule().getId());

        // Si la date de début n'est pas renseignée, on met la date du jour
        Long dateDebutBase = location.getDateDebut() == null ? new Date().getTime() : location.getDateDebut().getTime();
        values.put(LocationContract.COL_DEBUT, dateDebutBase);
        // La date de fin n'est pas obligatoire, elle sera mise automatiquement lors du retour
        Long dateFinBase = location.getDateDebut() == null ? null : location.getDateDebut().getTime();
        values.put(LocationContract.COL_FIN, dateFinBase);

        return db.update(LocationContract.TABLE_NAME, values, LocationContract.COL_ID + "=?",new
                String[]{String.valueOf(location.getId())})>0;
    }

    public boolean isVehiculeLoue(Long vehicule_id){
        boolean isLoue = false;
        long dateJour = new Date().getTime();
        Cursor cursor = db.query(
                LocationContract.TABLE_NAME,
                new String[]{LocationContract.COL_ID, LocationContract.COL_CLIENT_ID, LocationContract.COL_VEHICULE_ID, LocationContract.COL_DEBUT, LocationContract.COL_FIN},
                LocationContract.COL_VEHICULE_ID + " =? and " + LocationContract.COL_DEBUT + " <= ? and (" + LocationContract.COL_FIN + " > ? or " + LocationContract.COL_FIN + " is null)",
                new String[]{String.valueOf(vehicule_id), String.valueOf(dateJour), String.valueOf(dateJour)},
                null,
                null,
                null);

        if(cursor.moveToNext())
        {
            isLoue = true;
        }

        return isLoue;
    }

    /**
     * Si on a loué un véhicule dans le futur, il ne faut pas le bloquer
     * il faut le rendre disponible jusqu'à la date de début de la première location
     * on crée donc cette méthode pour récupérer la date de début de la futur location
     * a laquelle on retire un jour ca sera la date de fin de la location qu'on souhaite
     * @param vehicule_id
     * @return
     */
    public Date getLocationAVenir(Long vehicule_id){
        final long UN_JOUR = 1000 * 3600 * 24;
        Date dateRet = null;

        Cursor cursor = db.query(
                LocationContract.TABLE_NAME,
                new String[]{LocationContract.COL_DEBUT},
                LocationContract.COL_VEHICULE_ID + " =? and " + LocationContract.COL_DEBUT + " > ?",
                new String[]{String.valueOf(vehicule_id), String.valueOf(new Date().getTime())},
                null,
                null,
                LocationContract.COL_DEBUT);

        if(cursor.moveToNext())
        {
            long dateDebut = cursor.getLong(cursor.getColumnIndex(LocationContract.COL_DEBUT));
            dateRet = new Date(dateDebut - UN_JOUR);
        }

        return dateRet;
    }

    public Location getLocationEnCours(Long vehicule_id){
        Location location = null;
        long dateDuJour = new Date().getTime();
        Cursor cursor = db.query(
                LocationContract.TABLE_NAME,
                new String[]{LocationContract.COL_ID, LocationContract.COL_CLIENT_ID, LocationContract.COL_VEHICULE_ID, LocationContract.COL_DEBUT, LocationContract.COL_FIN},
                LocationContract.COL_VEHICULE_ID + " =? and " + LocationContract.COL_DEBUT + " <= ? and (" + LocationContract.COL_FIN + " > ? or " + LocationContract.COL_FIN + " is null)",
                new String[]{String.valueOf(vehicule_id), String.valueOf(dateDuJour), String.valueOf(dateDuJour)},
                null,
                null,
                null);
        while(cursor.moveToNext())
        {
            location = new Location();
            location.setId(cursor.getInt(cursor.getColumnIndex(LocationContract.COL_ID)));

            Client client = clientDAO.get(cursor.getLong(cursor.getColumnIndex(LocationContract.COL_CLIENT_ID)));
            location.setClient(client);

            Vehicule vehicule = vehiculeDAO.get(cursor.getLong(cursor.getColumnIndex(LocationContract.COL_VEHICULE_ID)));
            location.setVehicule(vehicule);

            Date dateDebut = new Date(cursor.getLong(cursor.getColumnIndex(LocationContract.COL_DEBUT)));
            location.setDateDebut(dateDebut);

            Date dateFin = null;
            if(!cursor.isNull(cursor.getColumnIndex(LocationContract.COL_FIN))) {
                dateFin = new Date(cursor.getLong(cursor.getColumnIndex(LocationContract.COL_FIN)));
            }
            location.setDateFin(dateFin);
        }
        return location;
    }

    public List<Location> getAll()
    {
        List<Location> resultat = new ArrayList<>();
        Cursor cursor = db.query(
                LocationContract.TABLE_NAME,
                new String[]{LocationContract.COL_ID, LocationContract.COL_CLIENT_ID, LocationContract.COL_VEHICULE_ID, LocationContract.COL_DEBUT, LocationContract.COL_FIN},
                null,
                null,
                null,
                null,
                null);
        while(cursor.moveToNext())
        {
            Location location = new Location();
            location.setId(cursor.getInt(cursor.getColumnIndex(LocationContract.COL_ID)));

            Client client = clientDAO.get(cursor.getLong(cursor.getColumnIndex(LocationContract.COL_CLIENT_ID)));
            location.setClient(client);

            Vehicule vehicule = vehiculeDAO.get(cursor.getLong(cursor.getColumnIndex(LocationContract.COL_VEHICULE_ID)));
            location.setVehicule(vehicule);

            Date dateDebut = new Date(cursor.getLong(cursor.getColumnIndex(LocationContract.COL_DEBUT)));
            location.setDateDebut(dateDebut);

            Date dateFin = new Date(cursor.getLong(cursor.getColumnIndex(LocationContract.COL_FIN)));
            location.setDateFin(dateFin);

            resultat.add(location);
        }
        return resultat;
    }

    public List<Location> getLocationsFutures(Long vehicule_id){
        List<Location> resultat = new ArrayList<>();
        long dateDuJour = new Date().getTime();
        Cursor cursor = db.query(
                LocationContract.TABLE_NAME,
                new String[]{LocationContract.COL_ID, LocationContract.COL_CLIENT_ID, LocationContract.COL_VEHICULE_ID, LocationContract.COL_DEBUT, LocationContract.COL_FIN},
                LocationContract.COL_VEHICULE_ID + " =? and " + LocationContract.COL_DEBUT + " > ?",
                new String[]{String.valueOf(vehicule_id), String.valueOf(dateDuJour)},
                null,
                null,
                null);
        while(cursor.moveToNext())
        {
            Location location = new Location();
            location.setId(cursor.getInt(cursor.getColumnIndex(LocationContract.COL_ID)));

            Client client = clientDAO.get(cursor.getLong(cursor.getColumnIndex(LocationContract.COL_CLIENT_ID)));
            location.setClient(client);

            Vehicule vehicule = vehiculeDAO.get(cursor.getLong(cursor.getColumnIndex(LocationContract.COL_VEHICULE_ID)));
            location.setVehicule(vehicule);

            Date dateDebut = new Date(cursor.getLong(cursor.getColumnIndex(LocationContract.COL_DEBUT)));
            location.setDateDebut(dateDebut);

            Date dateFin = new Date(cursor.getLong(cursor.getColumnIndex(LocationContract.COL_FIN)));
            location.setDateFin(dateFin);

            resultat.add(location);
        }
        return resultat;
    }
}
