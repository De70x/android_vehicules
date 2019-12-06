package fr.eni.lokacar.activities.location;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.MonthAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.eni.lokacar.R;
import fr.eni.lokacar.activities.client.CreationClientActivity;
import fr.eni.lokacar.activities.client.ListeClientsActivity;
import fr.eni.lokacar.activities.vehicule.ListeVehiculesActivity;
import fr.eni.lokacar.bo.client.Client;
import fr.eni.lokacar.bo.location.Location;
import fr.eni.lokacar.bo.vehicule.Vehicule;
import fr.eni.lokacar.dao.location.LocationDAO;

public class DebuterLocationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private Client client = null;
    private Vehicule vehicule = null;
    private Date dateDebut = null;
    private Date dateFin = null;
    private String action = null;

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

        long debutExtra = intent.getLongExtra("dateDebut", 0);
        this.dateDebut = debutExtra == 0 ? null : new Date(debutExtra);
        if (this.dateDebut != null) {
            btnDebut.setText(df.format(this.dateDebut));
        }

        // Si un client est sélectionné on l'affiche
        Client clientExtra = intent.getParcelableExtra("client");
        if (clientExtra != null) {
            this.client = clientExtra;
            String nomAffiche = client.getPrenom() + " " + client.getNom();
            nomClient.setText(nomAffiche);
            btnValider.setVisibility(View.VISIBLE);
        }

        this.vehicule = intent.getParcelableExtra("vehicule");
    }

    public void creerLocation(View view) {
        if (this.dateDebut == null)
            dateDebut = new Date();
        if (this.dateFin == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateDebut);
            cal.add(Calendar.DAY_OF_YEAR, 15);
            dateFin = cal.getTime();
        }
        Location location = new Location();

        location.setDateDebut(dateDebut);
        location.setDateFin(dateFin);
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
        Intent intent = new Intent(this, DebuterLocationActivity.class);
        intent.putExtra("client", this.client);
        LocationDAO locationDAO = new LocationDAO(this);

        int year = -1;
        int month = -1;
        int dayOfMonth = -1;
        if (this.dateDebut != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.dateDebut);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerDebut = DatePickerDialog.newInstance(this, year, month, dayOfMonth);
        datePickerDebut.setDisabledDays(getDatesIndispo());
        datePickerDebut.setMinDate(dateToCalendar(new Date()));
        if(dateFin != null) {
            datePickerDebut.setMaxDate(dateToCalendar(dateFin));
        }

        action = "debut";
        datePickerDebut.show(this.getFragmentManager(), "");
    }

    public void selectionnerDateFin(View view) {
        Intent intent = new Intent(this, DebuterLocationActivity.class);
        intent.putExtra("client", this.client);
        int year = -1;
        int month = -1;
        int dayOfMonth = -1;
        if (dateFin != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFin);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        }
        else if(dateDebut != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateDebut);
            cal.add(Calendar.DAY_OF_YEAR,1);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerFin = DatePickerDialog.newInstance(this, year, month, dayOfMonth);
        datePickerFin.setDisabledDays(getDatesIndispo());
        if(dateDebut != null)
            datePickerFin.setMinDate(dateToCalendar(dateDebut));
        else
            datePickerFin.setMinDate(dateToCalendar(new Date()));

        action = "fin";
        datePickerFin.show(this.getFragmentManager(), "");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int month, int dayOfMonth) {
        this.client = getIntent().getParcelableExtra("client");
        Date date = getDateFromDatePicker(view);
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);

        if("debut".equals(action)) {
            this.dateDebut = date;
            Button selectionDateDebut = findViewById(R.id.selection_date_debut);
            selectionDateDebut.setText(df.format(dateDebut));
        }
        if("fin".equals(action)) {
            this.dateFin = date;
            Button selectionDateFin = findViewById(R.id.selection_date_fin);
            selectionDateFin.setText(df.format(dateFin));
        }
    }

    public void creerClient(View view) {
        Intent intent = new Intent(this, CreationClientActivity.class);
        if (this.dateDebut != null)
            intent.putExtra("dateDebut", this.dateDebut.getTime());
        intent.putExtra("vehicule", this.vehicule);
        startActivity(intent);
    }

    public void selectionnerClient(View view) {
        Intent intent = new Intent(this, ListeClientsActivity.class);
        if (this.dateDebut != null)
            intent.putExtra("dateDebut", this.dateDebut.getTime());
        intent.putExtra("vehicule", this.vehicule);
        startActivity(intent);
    }


    public static java.util.Date getDateFromDatePicker(DatePickerDialog datePicker) {
        MonthAdapter.CalendarDay dateSelected = datePicker.getSelectedDay();
        int day = dateSelected.getDay();
        int month = dateSelected.getMonth();
        int year = dateSelected.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public Calendar[] getDatesIndispo(){
        LocationDAO locationDAO = new LocationDAO(this);
        List<Location> locationsFutures = locationDAO.getLocationsFutures((long)vehicule.getId());
        List<Calendar> datesIndispo = new ArrayList<>();

        for(Location location : locationsFutures){
            datesIndispo.addAll(datesBetween(location.getDateDebut(), location.getDateFin()));
        }
        Calendar[] c = new Calendar[datesIndispo.size()];
        return datesIndispo.toArray(c);
    }

    public List<Calendar> datesBetween(Date debut, Date fin){
        final long UN_JOUR = 1000 * 3600 * 24;
        List<Calendar> datesBetween = new ArrayList<>();
        long dateTemp = debut.getTime();
        while(dateTemp < fin.getTime() - UN_JOUR)
        {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(dateTemp);
            datesBetween.add(c);
            dateTemp += UN_JOUR;
        }

        return datesBetween;
    }

}
