package com.example.tukicmilan.auctions.milan.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.tukicmilan.auctions.milan.R;


public class SplashScreenActivity extends Activity implements View.OnClickListener{
    private boolean mIsBackButtonPressed;
    private static  int timeout = 3000;
    private boolean isSplashScreenVisible = true;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // open MainActivity if pref_splash_screen_visible settings is false
        isSplashScreenVisible = sharedPreferences.getBoolean(getString(R.string.pref_splash_screen_key), true);
        Log.i("isSplashScreenVisible", "" + isSplashScreenVisible);
        if (!isSplashScreenVisible) {
            openMainActivity();
            return;
        }else{
            setContentView(R.layout.activity_splash_screen);

            ImageView ivLogo = findViewById(R.id.ivSplashScreen);

            ivLogo.setOnClickListener(this);

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    finish();
                    if (!mIsBackButtonPressed) {
                        openMainActivity();
                    }
                }
            }, timeout);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        mIsBackButtonPressed = true;
        super.onBackPressed();
    }

    private void openMainActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, ItemsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivSplashScreen:
                openMainActivity();
                mIsBackButtonPressed = true;
                break;
        }
    }
}






