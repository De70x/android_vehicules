package fr.eni.lokacar.bo.vehicule;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.bo.vehicule.enumvehicule.Modele;

public class Vehicule implements Parcelable {

    private int id;
    private Modele modele;
    private String immatriculation;
    private float prixParJour;
    private List<String> photos;
    private boolean isLouee;

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public float getPrixParJour() {
        return prixParJour;
    }

    public void setPrixParJour(float prixParJour) {
        this.prixParJour = prixParJour;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public boolean isLouee() {
        return isLouee;
    }

    public void setLouee(boolean louee) {
        isLouee = louee;
    }

    public Vehicule(){
        setModele(Modele.CLIO);
        setPrixParJour(0.0f);
        setPhotos(new ArrayList<String>());
        setLouee(Boolean.FALSE);
    }

    public static final Creator<Vehicule> CREATOR = new Creator<Vehicule>() {
        @Override
        public Vehicule createFromParcel(Parcel in) {
            return new Vehicule(in);
        }

        @Override
        public Vehicule[] newArray(int size) {
            return new Vehicule[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    protected Vehicule(Parcel in) {
        id = in.readInt();
        modele = Modele.values()[in.readInt()];
        immatriculation = in.readString();
        prixParJour = in.readFloat();
        photos = in.createStringArrayList();
        isLouee = in.readInt() == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        int modeleParcel = modele == null ? -1 : modele.getId();
        dest.writeInt(modeleParcel);
        dest.writeString(immatriculation);
        dest.writeFloat(prixParJour);
        dest.writeStringList(photos);
        dest.writeInt(isLouee ? 1 : 0);
    }
}
