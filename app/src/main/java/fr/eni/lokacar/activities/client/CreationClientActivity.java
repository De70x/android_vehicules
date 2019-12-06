package fr.eni.lokacar.activities.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import fr.eni.lokacar.R;
import fr.eni.lokacar.activities.location.DebuterLocationActivity;
import fr.eni.lokacar.activities.vehicule.ListeVehiculesActivity;
import fr.eni.lokacar.bo.client.Client;
import fr.eni.lokacar.bo.client.ClientRecyclerAdapter;
import fr.eni.lokacar.bo.vehicule.Vehicule;
import fr.eni.lokacar.dao.client.ClientDAO;

public class CreationClientActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_ajouter);
    }

    public void ajouterClient(View view){
        Intent intentSource = getIntent();
        Long dateDebut = intentSource.getLongExtra("dateDebut", 0);
        Vehicule vehicule = intentSource.getParcelableExtra("vehicule");
        Client client = new Client();

        ClientDAO clientDAO = new ClientDAO(this);

        // le view en argument correspond au bouton, on prend donc l'élément root du bouton pour avoir accès aux autres données
        String nom = ((TextView)findViewById(R.id.nom_ajout)).getText().toString();
        String prenom = ((TextView)findViewById(R.id.prenom_ajout)).getText().toString();
        String mail = ((TextView)findViewById(R.id.mail_ajout)).getText().toString();
        String adresse = ((TextView)findViewById(R.id.adresse_ajout)).getText().toString();
        String telephone = ((TextView)findViewById(R.id.telephone_ajout)).getText().toString();

        client.setNom(nom);
        client.setPrenom(prenom);
        client.setMail(mail);
        client.setAdresse(adresse);
        client.setTelephone(telephone);

        clientDAO.insert(client);

        Intent intentDest = new Intent(this, DebuterLocationActivity.class);
        intentDest.putExtra("dateDebut", dateDebut);
        intentDest.putExtra("client", client);
        intentDest.putExtra("vehicule", vehicule);
        startActivity(intentDest);
    }

    public void annuler(View view) {
        Intent intentSource = getIntent();
        Long dateDebut = intentSource.getLongExtra("dateDebut", 0);
        Vehicule vehicule = intentSource.getParcelableExtra("vehicule");

        Intent intentDest = new Intent(this, DebuterLocationActivity.class);
        intentDest.putExtra("dateDebut", dateDebut);
        intentDest.putExtra("vehicule", vehicule);
        startActivity(intentDest);
    }
}
