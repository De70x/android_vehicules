package fr.eni.lokacar.dao.vehicule.photos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.customview.widget.ViewDragHelper;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.lokacar.bo.location.Location;
import fr.eni.lokacar.bo.vehicule.Vehicule;
import fr.eni.lokacar.bo.vehicule.photo.Photo;
import fr.eni.lokacar.dao.general.BDDHelper;
import fr.eni.lokacar.dao.location.LocationContract;
import fr.eni.lokacar.dao.vehicule.VehiculeDAO;

public class PhotoDAO {
    private SQLiteDatabase db;
    private BDDHelper bddHelper;
    private VehiculeDAO vehiculeDAO;

    public PhotoDAO(Context context)
    {
        bddHelper = new BDDHelper(context);
        db = bddHelper.getWritableDatabase();
        vehiculeDAO = new VehiculeDAO(context);
    }

    public long insert(Photo photo)
    {
        ContentValues values = new ContentValues();

        values.put(PhotoContract.COL_PATH, photo.getPath());
        values.put(PhotoContract.COL_VEHICULE, photo.getVehicule().getId());
        values.put(PhotoContract.COL_PHOTO, photo.getPhotoByteArray());

        return db.insert(PhotoContract.TABLE_NAME,null,values);
    }

    public List<Photo> getPhotosVehicule(Long vehicule_id){
        List<Photo> resultat = new ArrayList<>();
        Cursor cursor = db.query(
                PhotoContract.TABLE_NAME,
                new String[]{PhotoContract.COL_ID, PhotoContract.COL_PATH, PhotoContract.COL_PHOTO, PhotoContract.COL_VEHICULE},
                PhotoContract.COL_VEHICULE + " =?",
                new String[]{String.valueOf(vehicule_id)},
                null,
                null,
                null);
        while(cursor.moveToNext())
        {
            Photo photo = new Photo();
            photo.setId(cursor.getInt(cursor.getColumnIndex(PhotoContract.COL_ID)));
            photo.setPath(cursor.getString(cursor.getColumnIndex(PhotoContract.COL_PATH)));

            Vehicule vehicule = vehiculeDAO.get(cursor.getLong(cursor.getColumnIndex(PhotoContract.COL_VEHICULE)));
            photo.setVehicule(vehicule);

            byte[] photoByteArray=cursor.getBlob(cursor.getColumnIndex(PhotoContract.COL_PHOTO));
            ByteArrayInputStream imageStream = new ByteArrayInputStream(photoByteArray);
            Bitmap photoBitmap = BitmapFactory.decodeStream(imageStream);
            photo.setImagePhoto(photoBitmap);

            resultat.add(photo);
        }
        return resultat;
    }
}
