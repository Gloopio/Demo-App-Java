package io.gloop.tasks.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.gloop.Gloop;
import io.gloop.tasks.R;
import io.gloop.tasks.constants.Constants;

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
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splashscreen);
    }

    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // setup Gloop
        Gloop.initialize(this);

        // setup Gloop with debugging enabled
        // new Gloop(this, true);

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