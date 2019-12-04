package fr.eni.lokacar.activities.vehicule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.eni.lokacar.R;
import fr.eni.lokacar.bo.vehicule.Vehicule;
import fr.eni.lokacar.bo.vehicule.enumvehicule.Marque;
import fr.eni.lokacar.bo.vehicule.enumvehicule.Modele;
import fr.eni.lokacar.dao.vehicule.VehiculeDAO;

public class AjouterVehiculeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicule_ajouter);

        Spinner marque = findViewById(R.id.marque_ajout);
        Spinner modele = findViewById(R.id.modele_ajout);
        // On récupère la liste des marque, on crée un adapter pour les mettre dans le spinner
        ArrayAdapter<Marque> adapter = new ArrayAdapter<Marque>(getApplicationContext(), android.R.layout.simple_spinner_item, Marque.values());
        marque.setAdapter(adapter);
        marque.setOnItemSelectedListener(this);

        // Au départ, on rend la sélection du modèle impossible
        // on le la rend possible que si la marque est déjà sélectionnée
        modele.setEnabled(false);

        EditText immatriculation = findViewById(R.id.immatriculation_ajout);
        EditText prix = findViewById(R.id.prix_ajout);



        Vehicule vehicule = getIntent().getParcelableExtra("vehicule");
        boolean creation = getIntent().getBooleanExtra("creation", false);

        if (vehicule != null) {
            immatriculation.setText(vehicule.getImmatriculation());
            if(creation) {
                prix.setText(String.valueOf(vehicule.getPrixParJour()));

            }
            else {
                prix.setText(String.valueOf(vehicule.getPrixParJour()));
            }


        }
    }

    public void modifierVehicule(View view) {
        Intent intent = getIntent();
        Vehicule v = intent.getParcelableExtra("vehicule");
        boolean creation = intent.getBooleanExtra("creation", false);
        VehiculeDAO vDAO = new VehiculeDAO(this);

        // le view en argument correspond au bouton, on prend donc l'élément root du bouton pour avoir accès aux autres données
        String immatriculationModifiee = ((TextView)findViewById(R.id.immatriculation_ajout)).getText().toString();
        String prixModifie = ((TextView)findViewById(R.id.prix_ajout)).getText().toString();
        Modele modeleModifie = ((Modele)((Spinner)findViewById(R.id.modele_ajout)).getSelectedItem());

        if(v != null) {
            v.setModele(modeleModifie);
            v.setPrixParJour(Float.parseFloat(prixModifie));
            v.setImmatriculation(immatriculationModifiee);

            if(creation)
            {
                long id = vDAO.insert(v);
            }
            else
            {
                boolean updateOK =  vDAO.update(v);
            }
        }

        Intent intent2 = new Intent(this, ListeVehiculesActivity.class);
        startActivity(intent2);
    }

    public void annuler(View view) {
        Intent intent2 = new Intent(this, ListeVehiculesActivity.class);
        startActivity(intent2);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinnerModele = findViewById(R.id.modele_ajout);
        Spinner marque = findViewById(R.id.marque_ajout);
        Long marqueSelectionnee = marque.getAdapter().getItemId(position);
        List<Modele> modeles = Arrays.asList(Modele.values());
        List<Modele> modelesDansSpinner = new ArrayList<>();

        for(int i=0; i<modeles.size(); i++){
            if (modeles.get(i).getMarque().getId() == marqueSelectionnee){
                modelesDansSpinner.add(modeles.get(i));
            }
        }
        // On ajoute un choix vide dans la liste
        ArrayAdapter<Modele> adapter = new ArrayAdapter<Modele>(getApplicationContext(), android.R.layout.simple_spinner_item, modelesDansSpinner);
        spinnerModele.setAdapter(adapter);
        spinnerModele.setEnabled(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Spinner modele = findViewById(R.id.modele_ajout);
        modele.setEnabled(false);
    }
}
