package fr.eni.lokacar.bo.vehicule;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.NumberFormat;
import java.util.List;

import fr.eni.lokacar.R;
import fr.eni.lokacar.activities.location.DebuterLocationActivity;
import fr.eni.lokacar.activities.location.RetourLocationActivity;

public class VehiculeRecyclerAdapter extends RecyclerView.Adapter<VehiculeRecyclerAdapter.VehiculeRecyclerViewHolder> {
    private List<Vehicule> mDataSet;
    private OnClicSurUnItem action;

    public class VehiculeRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView marque;
        TextView prix;
        TextView immatriculation;
        ImageView photo;
        ImageButton isLouee;
        RatingBar note;

        public VehiculeRecyclerViewHolder(View v){
            super(v);
            marque = v.findViewById(R.id.marque);
            immatriculation = v.findViewById(R.id.immatriculation);
            prix = v.findViewById(R.id.prix);
            photo = v.findViewById(R.id.photoPrincipale);
            isLouee = v.findViewById(R.id.isLouee);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            action.onInteraction(mDataSet.get(VehiculeRecyclerViewHolder.this.getAdapterPosition()));
        }
    }

    public interface OnClicSurUnItem<Vehicule>{
        void onInteraction(Vehicule vehicule);
    }

    public VehiculeRecyclerAdapter(List<Vehicule> myDataset, OnClicSurUnItem activite) {
        mDataSet = myDataset;
        action = activite;
    }

    @NonNull
    @Override
    public VehiculeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicule_dans_liste, parent, false);
        return new VehiculeRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiculeRecyclerViewHolder holder, final int position) {

        /*** MARQUE / MODELE ***/
        String marqueModele = mDataSet.get(position).getModele().getMarque().toString() + " / " + mDataSet.get(position).getModele().toString();
        holder.marque.setText(marqueModele);

        /*** PRIX ***/
        // On utilise un formateur pour les prix
        final NumberFormat instance = NumberFormat.getNumberInstance();
        instance.setMinimumFractionDigits(2);
        instance.setMaximumFractionDigits(2);
        String prixParJour = "Prix par jour : " + instance.format(mDataSet.get(position).getPrixParJour()) + " €";
        holder.prix.setText(prixParJour);

        /*** IMMATRICULATION ***/
        holder.immatriculation.setText(mDataSet.get(position).getImmatriculation());

        /*** PHOTO ***/
/*        // On récupère le path de la première image
        String filepath = mDataSet.get(position).getPhotos().get(0);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // down sizing image as it throws OutOfMemory  Exception for larger images
        filepath = filepath.replace("file://", ""); // remove to avoid  BitmapFactory.decodeFile return null
        File imgFile = new File(filepath);
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),  options);
            holder.photo.setImageBitmap(bitmap);
        }*/

        /*** IMAGE BOUTON LOCATION ***/
        // si la voiture est louée on affiche la voiture rouge, sinon on affiche la verte
        if(mDataSet.get(position).isLouee()){
            holder.isLouee.setImageResource(R.drawable.voiture_louee);
            holder.isLouee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Il faut faire revenir le vehicule. Donc on met une date de fin à la location
                    // on passe le isLouee à false
                    Intent intent = new Intent(v.getContext(), RetourLocationActivity.class);
                    intent.putExtra("vehicule", mDataSet.get(position));
                    v.getContext().startActivity(intent);
                }
            });
        }
        else{
            holder.isLouee.setImageResource(R.drawable.voiture_libre);
            holder.isLouee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // On ouvre la page de création de la location
                    Intent intent = new Intent(v.getContext(), DebuterLocationActivity.class);
                    intent.putExtra("vehicule", mDataSet.get(position));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
