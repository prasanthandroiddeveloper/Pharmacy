package com.example.pharmacy.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.ApiClient;
import com.example.pharmacy.ApiInterface;
import com.example.pharmacy.Pojo.UserRegistration;
import com.example.pharmacy.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button Register;
    EditText name,phno,email,pwd;
    String sname,sphno,semail,spwd;
    LinearLayout MainLayt;
    JsonObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MainLayt = findViewById(R.id.mainLayt);

        Register = findViewById(R.id.registerBtn);
        name = findViewById(R.id.nameET);
        phno = findViewById(R.id.PhnoET);
        email = findViewById(R.id.emailET);
        pwd = findViewById(R.id.pwdEt);
        Register.setOnClickListener(view -> verifydata());

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


    }
    private void verifydata() {
        sname = name.getText().toString();
        sphno = phno.getText().toString();
        semail = email.getText().toString();
        spwd = pwd.getText().toString();

        if(sname.equals("")){
            Snackbar.make(MainLayt, "Enter Name", Snackbar.LENGTH_SHORT).show();
        }else if(semail.equals("") || !android.util.Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
            Snackbar.make(MainLayt, semail.equals("") ? "Enter Email" : "Enter vaild Email id ", Snackbar.LENGTH_SHORT).show();
        }else if(spwd.equals("") || spwd.length()<8){
            Snackbar.make(MainLayt, spwd.equals("") ? "Enter Password" : "Password Should be more than 8 Cahracters", Snackbar.LENGTH_SHORT).show();
        }else if(sphno.equals("") || !sphno.matches("[6-9][0-9]{9}")){
            Snackbar.make(MainLayt, sphno.equals("") ? "Enter Mobile Number" : "Enter Valid Mobile Number", Snackbar.LENGTH_SHORT).show();
        }else{

            insertdata();
            Snackbar.make(MainLayt, "Success", Snackbar.LENGTH_SHORT).show();
            Intent back1 = new Intent(RegisterActivity.this,BaseMainActivity.class);
            startActivity(back1);
        }
    }

    private void insertdata() {
        jsonObject = new JsonObject();
        jsonObject.addProperty("name", sname);
        jsonObject.addProperty("email", semail);
        jsonObject.addProperty("phoneno", sphno);
        jsonObject.addProperty("password", spwd);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserRegistration> call = apiInterface.postdata(sname,semail,sphno,spwd);
        call.enqueue(new Callback<UserRegistration>() {
            @Override
            public void onResponse(@NonNull Call<UserRegistration> call, @NonNull Response<UserRegistration> response) {
                Toast.makeText(RegisterActivity.this, new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();

                if(response.isSuccessful()){
                    assert response.body() != null;
                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserRegistration> call, Throwable t) {
                // Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}

