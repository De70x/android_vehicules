package fr.eni.lokacar.activities.vehicule;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import fr.eni.lokacar.R;

public class DetailVehiculeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicule_detail);
    }

    public void swapPhoto(View view) {

        ImageView imageGlissante = (ImageView) view;
        Integer xCurrentPos = imageGlissante.getLeft();
        Integer yCurrentPos = imageGlissante.getTop();

        Animation anim = new TranslateAnimation(xCurrentPos, xCurrentPos+150, yCurrentPos, yCurrentPos);
        anim.setDuration(1500);
        imageGlissante.startAnimation(anim);
    }
}
