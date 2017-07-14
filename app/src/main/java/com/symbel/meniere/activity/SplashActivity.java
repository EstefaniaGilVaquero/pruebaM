package com.symbel.meniere.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.symbel.meniere.R;
import com.symbel.meniere.utils.Utils;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private Activity activity;
    private String idioma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SET SCREEN ORIENTATION
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // SharedPreference.setMyContext(this);
        // HIDE TITLE
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // SET FULLSCREEN
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // SET LAYOUT
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
            chargeSplash();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkIdioma() {
        idioma = Locale.getDefault().getLanguage();
        if (idioma != null && !idioma.equalsIgnoreCase("es")) {
            idioma = "en";
        }
    }

    protected void chargeSplash(){
        try {

            // MAIN VARIABLES
            activity = this;

            checkIdioma();

        } catch (Exception e) {
            e.printStackTrace();
        }

        final Intent intent = new Intent(this, TabsActivity.class);

        int secs = 2; // Delay in seconds

        Utils.delay(secs, new Utils.DelayCallback() {
            @Override
            public void afterDelay() {
                // Do something after delay

                startActivity(intent);
                finish();

            }
        });


    }
}
