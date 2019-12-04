package fr.eni.lokacar.activities.location;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

import fr.eni.lokacar.R;
import fr.eni.lokacar.activities.vehicule.ListeVehiculesActivity;

public class DebuterLocationActivity extends AppCompatActivity {

    // Mettre un message d'avertissement à la création pour indiquer si une location est prévue dans l'avenir

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_debuter);
        DatePicker datePickerDebut = findViewById(R.id.picker_date_debut);

        datePickerDebut.setMinDate(new Date().getTime());


    }

    public void creerLocation(View view) {
    }

    public void annuler(View view) {
        Intent intent2 = new Intent(this, ListeVehiculesActivity.class);
        startActivity(intent2);
    }

    public void selectionnerDateDebut(View view) {
        DatePickerDialog dateDebut = new DatePickerDialog(this);
    }

    public void validerDateDebut(View view){
        DatePicker datePickerDebut = findViewById(R.id.picker_date_debut);
        DatePicker datePickerFin = findViewById(R.id.picker_date_fin);
        // On récupère la date de début sélectionnée
        Date dateDebut = getDateFromDatePicker(datePickerDebut);
        // sela fera le min de notre date de fin
        datePickerFin.setMinDate(dateDebut.getTime());
    }

    public void selectionnerDateFin(View view) {
        DatePickerDialog datePickerDebut = new DatePickerDialog(this);
    }

    /**
     * @param datePicker
     * @return a java.util.Date
     */
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
