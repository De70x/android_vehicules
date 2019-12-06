package fr.eni.lokacar.activities.location;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import fr.eni.lokacar.R;
import fr.eni.lokacar.activities.vehicule.ListeVehiculesActivity;
import fr.eni.lokacar.bo.location.Location;
import fr.eni.lokacar.bo.vehicule.Vehicule;
import fr.eni.lokacar.dao.location.LocationDAO;


public class RetourLocationActivity extends AppCompatActivity {

    Location locationEnCours = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_retour);
        Intent intent = getIntent();
        LocationDAO locationDAO = new LocationDAO(this);
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);


        Vehicule vehiculeExtra = intent.getParcelableExtra("vehicule");
        locationEnCours = locationDAO.getLocationEnCours((long) vehiculeExtra.getId());

        TextView clientI = findViewById(R.id.client);
        TextView vehiculeI = findViewById(R.id.vehicule);
        TextView debutI = findViewById(R.id.date_debut);
        TextView finI = findViewById(R.id.date_fin);
        TextView nbJoursI = findViewById(R.id.nb_jours);
        TextView coutI = findViewById(R.id.cout_location);

        String clientString = "Location pour : " + locationEnCours.getClient().getNom() + ", " + locationEnCours.getClient().getPrenom();
        clientI.setText(clientString);

        String vehiculeString = "De la voiture : " + locationEnCours.getVehicule().getModele().toString() + " d'immatriculation : " + locationEnCours.getVehicule().getImmatriculation();
        vehiculeI.setText(vehiculeString);

        String debutString = "Date de début de location : " + df.format(locationEnCours.getDateDebut());
        debutI.setText(debutString);

        String finString = "Date de fin de location : " + df.format(new Date());
        finI.setText(finString);

        String jours = nbJourLocation(locationEnCours) == 1 ? " jour" : " jours";
        String nbJourString = "Ce qui fait : " + nbJourLocation(locationEnCours) + jours;
        nbJoursI.setText(nbJourString);

        String coutString = calculerCoutLocation(locationEnCours).toString();
        coutI.setText(coutString);
    }

    private Float calculerCoutLocation(Location location){
        return nbJourLocation(location) * location.getVehicule().getPrixParJour();
    }

    private int nbJourLocation(Location location){
        final long UN_JOUR = 1000 * 3600 * 24;
        long dateDebut = location.getDateDebut().getTime();
        long dateFin = new Date().getTime();

        return Math.round((dateFin - dateDebut) / UN_JOUR + 0.5f);
    }

    public void annuler(View view) {
        Intent intent = new Intent(this, ListeVehiculesActivity.class);
        startActivity(intent);
    }

    public void retourLocation(View view) {
        Intent intent = new Intent(this, ListeVehiculesActivity.class);

        LocationDAO locationDAO = new LocationDAO(this);
        locationEnCours.setDateFin(new Date());
        locationDAO.update(locationEnCours);

        startActivity(intent);
    }
}
