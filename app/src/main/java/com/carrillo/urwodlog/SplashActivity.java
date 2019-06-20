package com.carrillo.urwodlog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {
    private String TAG = "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    /**
     * OnResume() - will handle the intro / splash screen - "Do you even lift bro?" screen
     */
    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "onResume");
        try{
            Thread.sleep(2000);

        }catch (Exception e){
            Log.i(TAG, "***Exception " + e.getMessage());
        }

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }


}
