package com.example.pharmacy.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegistration {

    @SerializedName("user_name")
    @Expose
    public String username;

    @SerializedName("user_email")
    @Expose
    public String email;

    @SerializedName("user_mobile")
    @Expose
    public String mobile;

    @SerializedName("user_password")
    @Expose
    public String password;


    public String getusername() {
        return username;
    }

    public void setusername(String uname) {
        this.username = uname;
    }

    public String getuseremail() {
        return email;
    }

    public void setuseremail(String uemail) {
        this.email = uemail;
    }

    public String getusermobile() {
        return mobile;
    }

    public void setusermobile(String umobile) {
        this.mobile = umobile;
    }

    public String getuserpassword() {
        return password;
    }

    public void setuserpassword(String upassword) {
        this.password = upassword;
    }

}
