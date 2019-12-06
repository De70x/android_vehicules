package fr.eni.lokacar.bo.vehicule.photo;

import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import fr.eni.lokacar.bo.vehicule.Vehicule;

public class Photo {

    private int id;
    private Vehicule vehicule;
    private String path;
    private Bitmap imagePhoto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getImagePhoto() {
        return imagePhoto;
    }

    public void setImagePhoto(Bitmap imagePhoto) {
        this.imagePhoto = imagePhoto;
    }

    /**
     * Cette méthode permet de retourner la photo sous forme de byte[]
     * @return
     */
    public byte[] getPhotoByteArray(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagePhoto.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
