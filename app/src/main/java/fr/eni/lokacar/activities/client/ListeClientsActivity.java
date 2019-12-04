package fr.eni.lokacar.activities.client;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.eni.lokacar.R;
import fr.eni.lokacar.activities.location.DebuterLocationActivity;
import fr.eni.lokacar.bo.client.Client;
import fr.eni.lokacar.bo.client.ClientRecyclerAdapter;
import fr.eni.lokacar.bo.vehicule.Vehicule;
import fr.eni.lokacar.dao.client.ClientDAO;

public class ListeClientsActivity extends AppCompatActivity implements ClientRecyclerAdapter.OnClicSurUnItem<Client> {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_liste);
        recyclerView = findViewById(R.id.clientRecycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ClientDAO clientDAO = new ClientDAO(this);
        mAdapter = new ClientRecyclerAdapter(clientDAO.getAll(), this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onInteraction(Client client) {
        Intent intentSource = getIntent();
        Long dateDebut = intentSource.getLongExtra("dateDebut", 0);
        Long dateFin = intentSource.getLongExtra("dateFin", 0);
        Vehicule vehicule = intentSource.getParcelableExtra("vehicule");

        Intent intentDest = new Intent(this, DebuterLocationActivity.class);
        intentDest.putExtra("dateDebut", dateDebut);
        intentDest.putExtra("dateFin", dateFin);
        intentDest.putExtra("client", client);
        intentDest.putExtra("vehicule", vehicule);
        startActivity(intentDest);
    }
}
