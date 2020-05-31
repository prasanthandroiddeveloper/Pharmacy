package com.example.pharmacy;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacy.Pojo.DrugAddress;
import com.example.pharmacy.utils.ViewMapActivity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.maps.android.SphericalUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MSearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MedicineAdapter recyclerAdapter;
    List<DrugAddress> address;
    String MName;
    String[] str1,cnctnum,km,name;
    float[] lati,longi,slati,slongi;
    double latitude,longitude,distance,kms;

    float dist;

    Bundle b;

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msearch);


        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        locationManager  = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(this, "no", Toast.LENGTH_SHORT).show();
            } else {
                getCurrentLocation();
            }
        }

        assert b != null;
        b = getIntent().getExtras();

        assert b != null;
        MName = b.getString("Medicinename");
        Log.i("mname",MName);
        getdata();
    }


    private void getCurrentLocation() {



        if (ActivityCompat.checkSelfPermission(
                MSearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MSearchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }else{
            LocationRequest locationRequest =new LocationRequest();
            locationRequest.setInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.getFusedLocationProviderClient(MSearchActivity.this).
                    requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            LocationServices.getFusedLocationProviderClient(MSearchActivity.this)
                                    .removeLocationUpdates(this);
                            if (locationResult != null && locationResult.getLocations().size() > 0) {
                                int latestLocationIndex = locationResult.getLocations().size() - 1;
                                 latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                                 longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();


                            /*    double lat=13.68325;
                                double lg=79.34719399999994;*/
                                Log.i("la", String.valueOf(latitude));
                                Log.i("lt", String.valueOf(longitude));

                              //  Toast.makeText(MSearchActivity.this, latitude +"" +longitude,Toast.LENGTH_SHORT).show();



                               /* LatLng from = new LatLng(latitude,longitude);
                                LatLng to = new LatLng(lat,lg);

                                //Calculating the distance in meters
                                double distance = SphericalUtil.computeDistanceBetween(from, to);
                                Log.i("ds", String.valueOf(distance));*/

                                //Displaying the distance
                              //  Toast.makeText(MSearchActivity.this, String.valueOf(distance), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }, Looper.getMainLooper());
        }}

    private void getdata() {
        recyclerView =  findViewById(R.id.srecylr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<DrugAddress>> call = apiService.postmname(MName);
        call.enqueue(new Callback<List<DrugAddress>>() {
            @Override
            public void onResponse(@NonNull Call<List<DrugAddress>> call, @NonNull Response<List<DrugAddress>> response) {
                 address = response.body();
                if(response.code() == 200){

                     str1 = new String[address.size()];
                     cnctnum = new String[address.size()];
                     name = new String[address.size()];
                     lati=new float[address.size()];
                     longi=new float[address.size()];
                     slati=new float[address.size()];
                     slongi=new float[address.size()];

                    for(int i=0; i<address.size(); i++){

                        //Storing names to string array
                        str1[i] = address.get(i).getPharAddress();
                        cnctnum[i] = address.get(i).getContactNum();
                        name[i] = address.get(i).getPharName();
                        lati[i]=address.get(i).getLatitude();
                        longi[i]=address.get(i).getLongitude();
                        slati[i]=address.get(i).getSourcelatitude();
                        slongi[i]=address.get(i).getSourcelongitude();

                        /*LatLng from = new LatLng(slati[i],slongi[i]);
                        LatLng to = new LatLng(lati[i],longi[i]);
                         distance = SphericalUtil.computeDistanceBetween(from, to);*/

                        /*double earthRadius = 6371000; //meters
                        double dLat = Math.toRadians( address.get(i).getSourcelatitude()-address.get(i).getLatitude() );
                        double dLng = Math.toRadians(address.get(i).getSourcelongitude()-address.get(i).getLongitude() );
                        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                                Math.cos(Math.toRadians(address.get(i).getSourcelatitude())) * Math.cos(Math.toRadians(address.get(i).getLatitude())) *
                                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
                        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                         dist = (float) (earthRadius * c);*/

                        double earthRadius = 3958.75;

                        double dLat = Math.toRadians(lati[i] - slati[i]);

                        double dLng = Math.toRadians(longi[i] - slongi[i]);

                        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                                + Math.cos(Math.toRadians(slati[i]))
                                * Math.cos(Math.toRadians(lati[i])) * Math.sin(dLng / 2)
                                * Math.sin(dLng / 2);

                        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

                        double dist = earthRadius * c;

                        int meterConversion = 1609;

                        // new Float(dist * meterConversion).floatValue();

                         kms= dist * meterConversion;

                    }
                    Toast.makeText(MSearchActivity.this, new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();
                    recyclerAdapter = new MedicineAdapter(getApplicationContext(),address);
                    recyclerView.setAdapter(recyclerAdapter);
                }else{
                    Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<DrugAddress>>call, Throwable t) {
                Log.e("error", t.toString());
                Toast.makeText(MSearchActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

        private List<DrugAddress> address;
        Context con;

        MedicineAdapter( Context c,List<DrugAddress> examples) {
            this.con = c;
            this.address = examples;
        }

        @NonNull @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_shop_names,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.addres.setText(address.get(position).getPharAddress());
            holder.cnctno.setText(address.get(position).getContactNum());
            holder.shopname.setText(address.get(position).getPharName());
            holder.cnctno1.setText(String.valueOf(kms));



            holder.cnctno.setOnClickListener(view -> {

                String ss=address.get(position).getContactNum();

                new AlertDialog.Builder(con)
                        .setMessage("Do you want to Dial")
                        .setPositiveButton("Yes", (dialog, id) -> {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"+ss));
                            con.startActivity(intent);
                        })
                        .setNegativeButton("No", (dialog, id) -> { })
                        .setCancelable(true).create().show();
            });

        }

        @Override
        public int getItemCount() { return address.size(); }


        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView addres,cnctno,km,map,shopname,cnctno1;
            int j;


            ViewHolder(View iv) {
                super(iv);
                con = iv.getContext();

                addres=  iv.findViewById(R.id.addressTv);
                shopname=  iv.findViewById(R.id.shopnametv);
                cnctno=  iv.findViewById(R.id.CnctNumTv);
                cnctno1=  iv.findViewById(R.id.CnctNumTv1);
                map=  iv.findViewById(R.id.viewMapTv);
                map.setOnClickListener(this);


            }

            @Override
            public void onClick(View view) {
                if(view==map) {
                    Toast.makeText(con, "clicked", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(con, ViewMapActivity.class);

                    j =getAdapterPosition();
                    String s= address.get(j).getPharAddress();
                    i.putExtra("address",s);

                    startActivity(i);
                    Toast.makeText(con, s, Toast.LENGTH_SHORT).show();

                }

            }
        }
    }
}
