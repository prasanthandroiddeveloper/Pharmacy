package com.example.pharmacy;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pharmacy.Pojo.MedicineSearch;
import com.example.pharmacy.utils.Constants;
import com.example.pharmacy.utils.MSearchActivity1;
import com.example.pharmacy.utils.SharedPrefs;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.pharmacy.utils.Constants.INTERNET_INTENT_REQUEST;
import static com.example.pharmacy.utils.Constants.REQUEST_LOCATION;
import static com.example.pharmacy.utils.Utils.internetConnection.checkConnection;

public class AutosearchActivity extends AppCompatActivity {
    AutoCompleteTextView search;
    List<MedicineSearch> examples;
    String Mname,MNameId,Medicinename,usg;
    List<String> lstname,idsList,desclst;
    TextView usage,htu,seffects,precautions,dosage,storage,mg;
    Button shopaddrs;
    String[] str1,str2,str3,str4,str5,str6,str7;
    LinearLayout info;
    SharedPrefs sp;
    int userid;
    LocationManager locationManager;
    double latitude,longitude;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autosearch);
        search=findViewById(R.id.searchtv);
        usage = findViewById(R.id.usageTV);
        htu = findViewById(R.id.htuTV);
        seffects = findViewById(R.id.effectsTV);
        precautions = findViewById(R.id.precautionsTV);
        dosage = findViewById(R.id.dosageTV);
        storage = findViewById(R.id.storageTV);
        mg = findViewById(R.id.mgTV);
        shopaddrs = findViewById(R.id.searchBtn);
        info = findViewById(R.id.infollyt);


        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_LOCATION);

        locationManager  = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
               // Toast.makeText(this, "no", Toast.LENGTH_SHORT).show();

                Toast.makeText(this, "Please On your Mobile Services", Toast.LENGTH_SHORT).show();
            } else {
                getCurrentLocation();
            }
        }
        sp=new SharedPrefs(this);
        userid=sp.getUTypeId();

        Log.i("us", String.valueOf(userid));
        checkinrntconn();


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        shopaddrs.setOnClickListener(view -> passdata());
        autodata();
    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(
                AutosearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                AutosearchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }else{
            LocationRequest locationRequest =new LocationRequest();
            locationRequest.setInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.getFusedLocationProviderClient(AutosearchActivity.this).
                    requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            LocationServices.getFusedLocationProviderClient(AutosearchActivity.this)
                                    .removeLocationUpdates(this);
                            if (locationResult != null && locationResult.getLocations().size() > 0) {
                                int latestLocationIndex = locationResult.getLocations().size() - 1;
                                latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                                longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();

                                Log.i("tenali",latitude+"\n"+longitude);
                                updatelatlong();


                            }
                        }
                    }, Looper.getMainLooper());
        }}



    private void updatelatlong(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://tripnetra.com/prasanth/androidphpfiles/Parmacyy/updatelonglat.php",response -> {

            Log.i("ts",response);
           Toast.makeText(this, response, Toast.LENGTH_SHORT).show();

        }, error -> {
            Toast.makeText(AutosearchActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));
                Log.i("guntur", String.valueOf(params));
                return params;
            }
        };

        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(AutosearchActivity.this));
        requestQueue.add(stringRequest);

    }


    private void autodata() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<MedicineSearch>> call = apiService.getFoodieData();
        call.enqueue(new Callback<List<MedicineSearch>>() {
            @Override
            public void onResponse(Call<List<MedicineSearch>> call, Response<List<MedicineSearch>> response) {
                examples = response.body();
                if(response.code() == 200){
                    lstname = new ArrayList<>(examples.size());
                    idsList = new ArrayList<>(examples.size());
                    desclst = new ArrayList<>(examples.size());

                    for(int i=0; i<examples.size(); i++){

                        lstname.add( examples.get(i).getMedicineName());
                        idsList.add( examples.get(i).getMedicineId());

                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AutosearchActivity.this, R.layout.textview_layout, lstname);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    search.setThreshold(1);
                    search.setAdapter(dataAdapter);
                    search.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
                        Mname = String.valueOf(arg0.getItemAtPosition(arg2));
                        int ii = lstname.indexOf(Mname);
                        MNameId = idsList.get(ii);
                        Medicinename = lstname.get(ii);
                                Log.i("id",MNameId);
                                Log.i("Medicinename",Medicinename);
                                info.setVisibility(View.VISIBLE);
                                getdata();

                        search.clearFocus();
                        View view = AutosearchActivity.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) AutosearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                            assert imm != null;
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<MedicineSearch>>call, Throwable t) {
                Log.e("error", t.toString());
                Toast.makeText(AutosearchActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getdata() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<MedicineSearch>> call = apiService.getData(MNameId);
        call.enqueue(new Callback<List<MedicineSearch>>() {
            @Override
            public void onResponse(Call<List<MedicineSearch>> call, Response<List<MedicineSearch>> response) {
                examples = response.body();
                if(response.code() == 200){

                    Toast.makeText(AutosearchActivity.this, MNameId, Toast.LENGTH_SHORT).show();
                    str1 = new String[examples.size()];
                    str2 = new String[examples.size()];
                    str3 = new String[examples.size()];
                    str4 = new String[examples.size()];
                    str5 = new String[examples.size()];
                    str6 = new String[examples.size()];
                    str7 = new String[examples.size()];
                    for(int i=0; i<examples.size(); i++){

                        str1[i] = examples.get(i).getMedicineUsage();
                        str2[i] = examples.get(i).getHowToUse();
                        str3[i] = examples.get(i).getSideEffects();
                        str4[i] = examples.get(i).getPrecautions();
                        str5[i] = examples.get(i).getDosage();
                        str6[i] = examples.get(i).getStorage();
                        str7[i] = examples.get(i).getMedicineMg();
                        usg = str1[i];
                        usage.setText(usg);
                        htu.setText(str2[i]);
                        seffects.setText(str3[i]);
                        precautions.setText(str4[i]);
                        dosage.setText(str5[i]);
                        storage.setText(str6[i]);
                        mg.setText(str7[i]);

                    }

                }else{
                    Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<MedicineSearch>>call, Throwable t) {
                Log.e("error", t.toString());
                Toast.makeText(AutosearchActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkinrntconn(){
        if(checkConnection(AutosearchActivity.this)){
       //     Toast.makeText(this, "Internet is on", Toast.LENGTH_SHORT).show();
            Log.i("hi","on");

        } else{
            checkinternet();

        }
    }


    private void checkinternet(){
        new AlertDialog.Builder(AutosearchActivity.this)
                .setMessage("Please Connect to Internet")
                .setPositiveButton("OK", (dialog, id) ->
                        startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS),INTERNET_INTENT_REQUEST))
                .setCancelable(false).create().show();
    }


    private void passdata() {
        Bundle bb = new Bundle();
        Intent nxt = new Intent(AutosearchActivity.this, MSearchActivity1.class);
        bb.putString("Medicinename", Medicinename);
        nxt.putExtras(bb);
        startActivity(nxt);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        checkinrntconn();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onRestart() {
        super.onRestart();
        checkinrntconn();
    }
}
