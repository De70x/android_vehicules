package fr.eni.lokacar.activities.vehicule.photo;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import fr.eni.lokacar.R;
import fr.eni.lokacar.activities.vehicule.ListeVehiculesActivity;
import fr.eni.lokacar.bo.vehicule.Vehicule;
import fr.eni.lokacar.bo.vehicule.photo.Photo;
import fr.eni.lokacar.dao.vehicule.photos.PhotoDAO;

public class PhotoActivity extends AppCompatActivity {

    // Constante
    private static final int RETOUR_PRISE_PHOTO = 1;

    private Button prendrePhoto;
    private ImageView photoAffichee;
    private String photoPath;
    private Button sauverPhoto;
    private Bitmap photoBitmap;
    private Vehicule vehicule;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_prendre);
        vehicule = getIntent().getParcelableExtra("vehicule");
        initActivity();
    }

    private void initActivity(){
        prendrePhoto = findViewById(R.id.prendre_photo);
        photoAffichee = findViewById(R.id.photo_affichee);
        sauverPhoto = findViewById(R.id.save_photo);
        createOnClicPrendrePhoto();
        createOnClicSauverPhoto();
    }

    // https://www.codota.com/code/java/methods/android.content.ContentValues/put
    // Il faut peut être passé par objet puis DAO pour Blober l'image
    private void createOnClicSauverPhoto(){

        sauverPhoto.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoDAO photoDAO = new PhotoDAO(v.getContext());
                Photo photo = new Photo();
                photo.setPath(photoPath);
                photo.setVehicule(vehicule);
                photo.setImagePhoto(photoBitmap);
                photoDAO.insert(photo);
                Intent intent = new Intent(v.getContext(), ListeVehiculesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createOnClicPrendrePhoto(){
        prendrePhoto.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                prendrePhoto();
            }
        });
    }


    private void prendrePhoto(){
        // intent qui sert à ouvrir la prise de photo
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // contrôle que l'intent peut être géré
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRANCE).format(new Date());
            File photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File photoFile = File.createTempFile("photo"+time, ".jpg", photoDir);
                // enregistrer le chemin complet
                photoPath = photoFile.getAbsolutePath();
                // utilisation du provider pour créer l'URI
                Uri photoUri = FileProvider.getUriForFile(PhotoActivity.this, PhotoActivity.this.getApplicationContext().getPackageName()+".provider", photoFile);
                // transfert de l'URI
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                // On ouvre l'activité
                startActivityForResult(intent,RETOUR_PRISE_PHOTO );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * retour de l'appel de l'appareil photo (startActivityForResult)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Verification du code de retour
        if(RETOUR_PRISE_PHOTO == requestCode && RESULT_OK == resultCode){
            // On récupère l'image
            photoBitmap = BitmapFactory.decodeFile(photoPath);
            // Affichage de l'image
            photoAffichee.setImageBitmap(photoBitmap);
        }
    }
}
