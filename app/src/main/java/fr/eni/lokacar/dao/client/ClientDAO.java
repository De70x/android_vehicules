package fr.eni.lokacar.dao.client;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.bo.client.Client;

import fr.eni.lokacar.dao.general.BDDHelper;

public class ClientDAO {

    private SQLiteDatabase db;
    private BDDHelper bddHelper;

    public ClientDAO(Context context)
    {
        bddHelper = new BDDHelper(context);
        db = bddHelper.getWritableDatabase();
    }

    public long insert(Client client)
    {
        ContentValues values = new ContentValues();

        values.put(ClientContract.COL_NOM, client.getNom());
        values.put(ClientContract.COL_PRENOM, client.getPrenom());
        values.put(ClientContract.COL_MAIL, client.getMail());
        values.put(ClientContract.COL_ADRESSE, client.getAdresse());
        values.put(ClientContract.COL_TELEPHONE, client.getTelephone());

        return db.insert(ClientContract.TABLE_NAME,null,values);
    }

    public boolean update(Client client)
    {
        ContentValues values = new ContentValues();

        values.put(ClientContract.COL_NOM, client.getNom());
        values.put(ClientContract.COL_PRENOM, client.getPrenom());
        values.put(ClientContract.COL_MAIL, client.getMail());
        values.put(ClientContract.COL_ADRESSE, client.getAdresse());
        values.put(ClientContract.COL_TELEPHONE, client.getTelephone());

        return db.update(ClientContract.TABLE_NAME, values,ClientContract.COL_ID + "=?",new
                String[]{String.valueOf(client.getId())})>0;
    }

    public Client get(long id){
        Client client = null;
        Cursor cursor = db.query(
                ClientContract.TABLE_NAME,
                new String[]{ClientContract.COL_ID,ClientContract.COL_NOM, ClientContract.COL_PRENOM, ClientContract.COL_MAIL, ClientContract.COL_ADRESSE, ClientContract.COL_TELEPHONE},
                ClientContract.COL_ID + " =?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if(cursor.moveToNext())
        {
            client = new Client();
            client.setId(cursor.getInt(cursor.getColumnIndex(ClientContract.COL_ID)));
            client.setNom(cursor.getString(cursor.getColumnIndex(ClientContract.COL_NOM)));
            client.setPrenom(cursor.getString(cursor.getColumnIndex(ClientContract.COL_PRENOM)));
            client.setMail(cursor.getString(cursor.getColumnIndex(ClientContract.COL_MAIL)));
            client.setAdresse(cursor.getString(cursor.getColumnIndex(ClientContract.COL_ADRESSE)));
            client.setTelephone(cursor.getString(cursor.getColumnIndex(ClientContract.COL_TELEPHONE)));
        }
        return client;
    }

    public List<Client> getAll()
    {
        List<Client> resultat = new ArrayList<>();
        Cursor cursor = db.query(
                ClientContract.TABLE_NAME,
                new String[]{ClientContract.COL_ID,ClientContract.COL_NOM, ClientContract.COL_PRENOM, ClientContract.COL_MAIL, ClientContract.COL_ADRESSE, ClientContract.COL_TELEPHONE},
                null,
                null,
                null,
                null,
                null);
        while(cursor.moveToNext())
        {
            Client client = new Client();
            client.setId(cursor.getInt(cursor.getColumnIndex(ClientContract.COL_ID)));
            client.setNom(cursor.getString(cursor.getColumnIndex(ClientContract.COL_NOM)));
            client.setPrenom(cursor.getString(cursor.getColumnIndex(ClientContract.COL_PRENOM)));
            client.setMail(cursor.getString(cursor.getColumnIndex(ClientContract.COL_MAIL)));
            client.setAdresse(cursor.getString(cursor.getColumnIndex(ClientContract.COL_ADRESSE)));
            client.setTelephone(cursor.getString(cursor.getColumnIndex(ClientContract.COL_TELEPHONE)));

            resultat.add(client);
        }
        return resultat;
    }
}
