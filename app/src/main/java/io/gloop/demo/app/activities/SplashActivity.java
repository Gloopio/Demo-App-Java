package io.gloop.demo.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import io.gloop.Gloop;
import io.gloop.demo.app.R;
import io.gloop.demo.app.constants.Constants;

public class SplashActivity extends Activity {

    /**
     * Duration of wait
     **/
    private static final int SPLASH_DISPLAY_LENGTH = 1000;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);
    }

    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // TODO add cet for testing

        // setup Gloop
        new Gloop(this, "your-api-key-goes-here", "192.168.0.3:8080");

        /* New Handler to start the next Activity
         * and close this SplashActivity-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!logInWithRememberedUser()) {
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private boolean logInWithRememberedUser() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0); // 0 - for private mode

        String email = pref.getString(Constants.SHARED_PREFERENCES_USER_EMAIL, "");
        String password = pref.getString(Constants.SHARED_PREFERENCES_USER_PASSWORD, "");

        if (!email.equals("") && !password.equals("")) {
            if (Gloop.login(email, password)) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
                return true;
            }
        }
        return false;
    }
}