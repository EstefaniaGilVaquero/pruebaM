package com.example.stefy83.meniere.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.stefy83.meniere.R;
import com.example.stefy83.meniere.utils.Utils;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private Activity activity;
    private String idioma;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //creating thread that will sleep for 10 seconds
        Thread t=new Thread() {
            public void run() {

                try {
                    //sleep thread for 10 seconds, time in milliseconds
                    sleep(10000);

                    //start new activity
                    Intent i=new Intent(SplashActivity.this,TabsActivity.class);
                    startActivity(i);

                    //destroying Splash activity
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        //start thread
        t.start();
    }


    //Destroy Welcome_screen.java after it goes to next activity
    @Override
    protected void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
        finish();

    }






    private void checkIdioma() {
        idioma = Locale.getDefault().getLanguage();
        if (idioma != null && !idioma.equalsIgnoreCase("es")) {
            idioma = "en";
        }
    }

    protected void chargeSplash(){
        try {
            // SET SCREEN ORIENTATION
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            // SharedPreference.setMyContext(this);
            // HIDE TITLE
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            // SET FULLSCREEN
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            // SET LAYOUT
            setContentView(R.layout.activity_splash);

            // MAIN VARIABLES
            activity = this;

            //tvSplashLoading = (TextView) findViewById(R.id.tvSplashLoading);

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
