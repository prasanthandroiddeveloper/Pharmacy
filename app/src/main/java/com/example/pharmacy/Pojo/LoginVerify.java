package com.example.pharmacy.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginVerify {




    @SerializedName("user_password")
    @Expose
    public String password;

    @SerializedName("user_email")
    @Expose
    public String email;

    @SerializedName("user_id")
    @Expose
    public String uid;


    public String getuseremail() {
        return email;
    }

    public void setuseremail(String uemail) {
        this.email = uemail;
    }


    public String getuserpassword() {
        return password;
    }

    public void setuserpassword(String upassword) {
        this.password = upassword;
    }


    public String getuid() {
        return uid;
    }

    public void setuid(String upassword) {
        this.uid = upassword;
    }
}
