package com.example.pharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pharmacy.Login.BaseMainActivity;
import com.example.pharmacy.utils.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(){
            @Override
            public void run(){
                try {
                    sleep(Constants.SPLASH_TIMEOUT);
                    startActivity(new Intent(getApplicationContext(), BaseMainActivity.class));
                } catch (InterruptedException e) {
                    // e.printStackTrace();
                }
                finish();
            }
        }.start();
    }
}
