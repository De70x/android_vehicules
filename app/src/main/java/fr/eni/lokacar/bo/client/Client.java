package fr.eni.lokacar.bo.client;

import android.os.Parcel;
import android.os.Parcelable;

public class Client implements Parcelable {
    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String adresse;
    private String telephone;

    protected Client(Parcel in) {
        id = in.readInt();
        nom = in.readString();
        prenom = in.readString();
        mail = in.readString();
        adresse = in.readString();
        telephone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeString(mail);
        dest.writeString(adresse);
        dest.writeString(telephone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };
}
