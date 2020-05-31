package com.example.pharmacy.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.pharmacy.MSearchActivity;
import com.example.pharmacy.R;

import java.util.Objects;

public class ViewMapActivity extends AppCompatActivity {
   // private static final int MAPS_INTENT_KEY =86 ;
    String Address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);
        Address= Objects.requireNonNull(getIntent().getExtras()).getString("address");
        viewMapAddrs();
    }

    private void viewMapAddrs() {

        String url = "https://www.google.com/maps/search/?api=1&query="+Address;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        ViewMapActivity.this.finish();
        this.overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
    }

}


