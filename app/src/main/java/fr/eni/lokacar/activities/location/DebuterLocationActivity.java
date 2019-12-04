package fr.eni.lokacar.activities.location;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import fr.eni.lokacar.R;
import fr.eni.lokacar.activities.client.CreationClientActivity;
import fr.eni.lokacar.activities.client.ListeClientsActivity;
import fr.eni.lokacar.activities.vehicule.ListeVehiculesActivity;
import fr.eni.lokacar.bo.client.Client;
import fr.eni.lokacar.bo.location.Location;
import fr.eni.lokacar.bo.vehicule.Vehicule;
import fr.eni.lokacar.dao.location.LocationDAO;

public class DebuterLocationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{


    private Client client=null;
    private Vehicule vehicule=null;
    private Date dateDebut=null;
    private Date dateFin=null;

    // Mettre un message d'avertissement à la création pour indiquer si une location est prévue dans l'avenir

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_debuter);
        Intent intent = getIntent();
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
        TextView nomClient = findViewById(R.id.client);
        Button btnValider = findViewById(R.id.btn_valider);
        Button btnDebut = findViewById(R.id.selection_date_debut);
        Button btnFin = findViewById(R.id.selection_date_fin);

        long debutExtra = intent.getLongExtra("dateDebut", 0);
        this.dateDebut = debutExtra == 0 ? null : new Date(debutExtra);
        if(this.dateDebut != null)
        {
            btnDebut.setText(df.format(this.dateDebut));
            btnFin.setVisibility(View.VISIBLE);
        }

        long finExtra = intent.getLongExtra("dateFin", 0);
        this.dateFin = finExtra == 0 ? null : new Date(finExtra);
        if(this.dateFin != null)
        {
            btnFin.setText(df.format(this.dateFin));
        }

        // Si un client est sélectionné on l'affiche
        Client clientExtra = intent.getParcelableExtra("client");
        if(clientExtra != null)
        {
            this.client = clientExtra;
            String nomAffiche = client.getPrenom() + " " + client.getNom();
            nomClient.setText(nomAffiche);
            btnValider.setVisibility(View.VISIBLE);
        }

        this.vehicule = intent.getParcelableExtra("vehicule");
    }

    public void creerLocation(View view) {
        if(this.dateDebut == null)
            dateDebut = new Date();
        long dateDebut = this.dateDebut.getTime();
        if(this.dateFin == null)
                dateFin = new GregorianCalendar(3000,1,1).getTime();
        long dateFin = this.dateFin.getTime();
        Vehicule vehicule = this.vehicule;
        Client client = this.client;
        Location location = new Location();

        location.setDateDebut(new Date(dateDebut));
        location.setDateFin(new Date(dateFin));
        location.setVehicule(vehicule);
        location.setClient(client);

        LocationDAO locationDAO = new LocationDAO(this);

        locationDAO.insert(location);

        Intent intentDest = new Intent(this, ListeVehiculesActivity.class);
        startActivity(intentDest);
    }

    public void annuler(View view) {
        Intent intent2 = new Intent(this, ListeVehiculesActivity.class);
        startActivity(intent2);
    }

    public void selectionnerDateDebut(View view) {
        int year = -1;
        int month = -1;
        int dayOfMonth = -1;
        if (this.dateDebut!=null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.dateDebut);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dateDebut = new DatePickerDialog(this, DebuterLocationActivity.this, year, month, dayOfMonth);
        dateDebut.getDatePicker().setId(R.id.selection_date_debut);
        dateDebut.getDatePicker().setMinDate(new Date().getTime());
        dateDebut.show();
    }

    public void selectionnerDateFin(View view) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.dateDebut);

        if (this.dateFin!=null) {
            cal.setTime(this.dateFin);
        }

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dateFin = new DatePickerDialog(this, DebuterLocationActivity.this, year, month, dayOfMonth);
        dateFin.getDatePicker().setId(R.id.selection_date_fin);
        dateFin.getDatePicker().setMinDate(this.dateDebut.getTime());
        dateFin.show();
    }



    public void creerClient(View view) {
        Intent intent = new Intent(this, CreationClientActivity.class);
        if(this.dateDebut !=  null)
            intent.putExtra("dateDebut", this.dateDebut.getTime());
        if(this.dateFin != null)
            intent.putExtra("dateFin", this.dateFin.getTime());
        intent.putExtra("vehicule", this.vehicule);
        startActivity(intent);
    }

    public void selectionnerClient(View view) {
        Intent intent = new Intent(this, ListeClientsActivity.class);
        if(this.dateDebut !=  null)
            intent.putExtra("dateDebut", this.dateDebut.getTime());
        if(this.dateFin != null)
            intent.putExtra("dateFin", this.dateFin.getTime());
        intent.putExtra("vehicule", this.vehicule);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Date date = getDateFromDatePicker(view);
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
        if (view.getId() == R.id.selection_date_debut){
            this.dateDebut = date;
            Button selectionDateDebut = findViewById(R.id.selection_date_debut);
            Button selectionDateFin = findViewById(R.id.selection_date_fin);
            selectionDateDebut.setText(df.format(dateDebut));
            selectionDateFin.setVisibility(View.VISIBLE);
        }
        else if(view.getId() == R.id.selection_date_fin){
            this.dateFin = date;
            Button selectionDateFin = findViewById(R.id.selection_date_fin);
            selectionDateFin.setText(df.format(dateFin));
        }
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
