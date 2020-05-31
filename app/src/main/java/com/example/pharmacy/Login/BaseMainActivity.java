package com.example.pharmacy.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.ApiClient;
import com.example.pharmacy.ApiInterface;
import com.example.pharmacy.AutosearchActivity;
import com.example.pharmacy.Pojo.LoginVerify;
import com.example.pharmacy.R;
import com.example.pharmacy.utils.SharedPrefs;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseMainActivity extends AppCompatActivity {

    Button login;
    EditText email,pwd;
    String Fetchmail,Fetchpwd;
    RelativeLayout main;
    List<LoginVerify> verifyList;
    String [] str1;
    SharedPrefs sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_main);

        login = findViewById(R.id.LoginBtn);
        email = findViewById(R.id.emailET);
        pwd = findViewById(R.id.pwdET);
        main=findViewById(R.id.mainlyt);

        sp = new SharedPrefs(BaseMainActivity.this);
        if(sp.getLoggedin()){
            startActivity(new Intent(BaseMainActivity.this,AutosearchActivity.class));
            BaseMainActivity.this.finish();
        }


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        login.setOnClickListener(view -> validelogin());
    }

    private void validelogin() {

        Fetchmail = email.getText().toString().trim();
        Fetchpwd = pwd.getText().toString().trim();

        if(Fetchmail.equals("")){
            Snackbar.make(main,"Enter Email ",Snackbar.LENGTH_SHORT).show();
        }else if(Fetchpwd.equals("") || Fetchpwd.length()<8){
            Snackbar.make(main, "Enter Valid Pwd", Snackbar.LENGTH_SHORT).show();
        }else{

         verifylogin();
        }
    }

    public void viewRegisterClicked(View view) {
        Intent intent = new Intent(BaseMainActivity.this,RegisterActivity.class);
        startActivity(intent);

    }

    public void verifylogin() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LoginVerify>> call = apiService.verifydata(Fetchmail,Fetchpwd);
        call.enqueue(new Callback<List<LoginVerify>>() {
            @Override
            public void onResponse(Call<List<LoginVerify>> call, Response<List<LoginVerify>> response) {
                verifyList = response.body();



                if(response.code() == 200){
                    if (verifyList != null) {
                        str1 = new String[verifyList.size()];
                        for(int i=0; i<verifyList.size(); i++){

                            str1[i] = verifyList.get(i).getuid();
                            Log.i("str",str1[i]);
                            sp.setUTypeId(Integer.parseInt(str1[i]));
                            sp.setLoggedin(true);
                        }
                    }

                    Intent i1 = new Intent(BaseMainActivity.this, AutosearchActivity.class);
                    startActivity(i1);
                    Snackbar.make(main, "Success", Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(BaseMainActivity.this, new Gson().toJson(verifyList), Toast.LENGTH_SHORT).show();

                    }

                }


            @Override
            public void onFailure(Call<List<LoginVerify>> call, Throwable t) {
                Log.i("rp", Objects.requireNonNull(t.getMessage()));
            }
        });

    }}


