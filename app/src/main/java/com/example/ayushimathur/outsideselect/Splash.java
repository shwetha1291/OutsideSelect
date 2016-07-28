package com.example.ayushimathur.outsideselect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

/**
 * Created by ayushimathur on 7/24/16.
 */
public class Splash extends Activity {
    private static boolean splashLoaded = false;
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        if(!splashLoaded){
            setContentView(R.layout.splash);
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(Splash.this, SelectArtist.class));
                    finish();
                }
            }, secondsDelayed * 500);

            splashLoaded = true;
        }
        else {
            Intent goToMainActivity = new Intent(Splash.this, SelectArtist.class);
            goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToMainActivity);
            finish();
        }
    }
}
