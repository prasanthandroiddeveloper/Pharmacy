package com.example.pharmacy.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pharmacy.Pojo.DataAdapter;
import com.example.pharmacy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MSearchActivity1 extends AppCompatActivity {
    Bundle b;
    String MName;
    RecyclerView recyclerView;
    recycle_adapter recyclerAdapter;
    double latitude,longitude,distance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_search1);

        recyclerView=findViewById(R.id.srecylr);



        assert b != null;
        b = getIntent().getExtras();

        assert b != null;
        MName = b.getString("Medicinename");
        if (MName != null) {
            Log.i("mname",MName);
        }
        getdata();
    }

    private void getdata(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://tripnetra.com/prasanth/androidphpfiles/Parmacyy/fetch_address.php", response -> {

            Log.i("t",response);
            try {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(MSearchActivity1.this));
                JSONArray jarry = new JSONArray(response);
                List<DataAdapter> list = new ArrayList<>();
                for(int i=0;i<jarry.length();i++){
                    DataAdapter adapter = new DataAdapter();

                    JSONObject jobj = jarry.getJSONObject(i);

                    adapter.setPharid(jobj.getString("phar_id"));
                    adapter.setPharaddress(jobj.getString("phar_address"));
                    adapter.setcontactnum(jobj.getString("contact_num"));
                    adapter.setphardistance(jobj.getString("phar_distance"));
                    adapter.setpharname(jobj.getString("phar_name"));
                    adapter.setLatitude(latitude);
                    adapter.setlongitude(longitude);
                    adapter.setSourcelatitude(jobj.getDouble("sourcelatitude"));
                    adapter.setSourcelongitude(jobj.getDouble("sourcelongitude"));
                    adapter.setdistance(distance);

                    list.add(adapter);
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(MSearchActivity1.this));
                recyclerAdapter = new recycle_adapter(list);
                recyclerView.setAdapter(recyclerAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            Toast.makeText(MSearchActivity1.this,"something went wrong Try again 2",Toast.LENGTH_SHORT).show();
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("mname", MName);
                Log.i("iparams", String.valueOf(params));
                return params;
            }
        };

        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(MSearchActivity1.this));
        requestQueue.add(stringRequest);
    }

    private static class recycle_adapter extends RecyclerView.Adapter<recycle_adapter.ViewHolder>  {

        Context context;
        private   List<DataAdapter> listadapter;
         recycle_adapter(List<DataAdapter> list) {
            super();
            this.listadapter = list;

        }

        @NonNull
        @Override
        public recycle_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new recycle_adapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_shop_names, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull recycle_adapter.ViewHolder holder, int position) {
            DataAdapter DDataAdapter =  listadapter.get(position);
            holder.nameTV.setText(String.valueOf(DDataAdapter.getdistance()));

        }

        @Override
        public int getItemCount() {
            return listadapter.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameTV;
            ViewHolder(View iv) {
                super(iv);
                context = iv.getContext();
                nameTV = iv.findViewById(R.id.CnctNumTv1);


            }
        }
    }
}
