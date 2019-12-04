package fr.eni.lokacar.bo.location;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import fr.eni.lokacar.bo.client.Client;
import fr.eni.lokacar.bo.vehicule.Vehicule;


public class Location implements Parcelable {

    private int id;
    private Vehicule vehicule;
    private Client client;
    private Date dateDebut;
    private Date dateFin;

    public Location(){
        vehicule = null;
        client = null;
        dateDebut = new Date();
        dateFin = null;
    }

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    protected Location(Parcel in) {
        id = in.readInt();
        vehicule = in.readParcelable(Vehicule.class.getClassLoader());
        client = in.readParcelable(Client.class.getClassLoader());
        dateDebut = new Date(in.readLong());
        dateFin = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(vehicule, flags);
        dest.writeParcelable(client, flags);
        dest.writeLong(dateDebut.getTime());
        dest.writeLong(dateFin.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
