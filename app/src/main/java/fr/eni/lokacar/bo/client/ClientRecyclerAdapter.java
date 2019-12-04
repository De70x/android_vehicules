package fr.eni.lokacar.bo.client;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.eni.lokacar.R;

public class ClientRecyclerAdapter extends RecyclerView.Adapter<ClientRecyclerAdapter.ClientRecyclerViewHolder> {
    private List<Client> mDataSet;
    private OnClicSurUnItem action;

    public class ClientRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nom;
        TextView prenom;
        TextView mail;
        TextView adresse;
        TextView telephone;


        public ClientRecyclerViewHolder(View v){
            super(v);
            nom = v.findViewById(R.id.nom);
            prenom = v.findViewById(R.id.prenom);
            mail = v.findViewById(R.id.mail);
            adresse = v.findViewById(R.id.adresse);
            telephone = v.findViewById(R.id.telephone);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            action.onInteraction(mDataSet.get(ClientRecyclerViewHolder.this.getAdapterPosition()));
        }
    }

    public interface OnClicSurUnItem<T>{
        void onInteraction(T client);
    }

    public ClientRecyclerAdapter(List<Client> myDataset, OnClicSurUnItem activite) {
        mDataSet = myDataset;
        action = activite;
    }

    @NonNull
    @Override
    public ClientRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_dans_liste, parent, false);
        return new ClientRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientRecyclerViewHolder holder, final int position) {

        String nom = mDataSet.get(position).getNom();
        holder.nom.setText(nom);

        String prenom = mDataSet.get(position).getPrenom();
        holder.prenom.setText(prenom);

        String mail = mDataSet.get(position).getMail();
        holder.mail.setText(mail);

        String adresse = mDataSet.get(position).getAdresse();
        holder.adresse.setText(adresse);

        String telephone = mDataSet.get(position).getTelephone();
        holder.telephone.setText(telephone);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
