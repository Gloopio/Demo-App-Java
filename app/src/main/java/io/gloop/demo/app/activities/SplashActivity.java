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

    // Set url of the server.
//    private static final String HOST_URL = "192.168.0.10:8080";
    private static final String HOST_URL = "52.169.152.13:8080";

//    private static final String API_KEY = "your-api-key-goes-here";
    private static final String API_KEY = "b8ed4e84-4db5-4b63-b369-f75cbf51a065";

    private static final boolean DEBUG = true;

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
        setContentView(R.layout.activity_splashscreen);
    }

    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // setup Gloop
        new Gloop(this, API_KEY, HOST_URL);

        // setup Gloop with debugging enabled
        // new Gloop(this, "your-api-key-goes-here", HOST_URL, DEBUG);

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

        if (!email.isEmpty() && !password.isEmpty()) {
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