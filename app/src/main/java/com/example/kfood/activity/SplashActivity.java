package com.example.kfood.activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kfood.R;


public class SplashActivity extends AppCompatActivity {
    final long SPLASH_TIME_OUT = 2000;
    final Runnable action = new Runnable() {
        @Override
        public void run() {
            Intent gomain = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(gomain);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        if (isConnected()){
            handler.postDelayed(action, SPLASH_TIME_OUT);    
        }else {
            Toast.makeText(this, "Please Connect to network!", Toast.LENGTH_SHORT).show();
        }
        
    }
    
    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
