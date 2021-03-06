package fr.eni.lokacar.activities.vehicule;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.eni.lokacar.R;
import fr.eni.lokacar.activities.vehicule.photo.PhotoActivity;
import fr.eni.lokacar.bo.vehicule.Vehicule;
import fr.eni.lokacar.bo.vehicule.VehiculeRecyclerAdapter;
import fr.eni.lokacar.dao.vehicule.VehiculeDAO;

public class ListeVehiculesActivity extends AppCompatActivity implements VehiculeRecyclerAdapter.OnClicSurUnItem<Vehicule> {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicule_liste);

        recyclerView = findViewById(R.id.vehiculeRecycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        VehiculeDAO vehiculeDAO = new VehiculeDAO(this);
        mAdapter = new VehiculeRecyclerAdapter(vehiculeDAO.getAll(), this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onInteraction(Vehicule vehicule) {
        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("vehicule", vehicule);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void ajouterVehicule(View view) {
        Intent intent = new Intent(this, AjouterVehiculeActivity.class);
        intent.putExtra("vehicule", new Vehicule());
        intent.putExtra("creation", true);
        startActivity(intent);
    }

    public void swapPhoto(View view) {
        Intent intent = new Intent(view.getContext(), DetailVehiculeActivity.class);
        startActivity(intent);
    }
}
