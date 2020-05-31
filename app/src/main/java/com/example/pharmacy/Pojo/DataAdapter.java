package com.example.pharmacy.Pojo;

public class DataAdapter {

    private String pharid, phar_address, contact_num, phar_distance, phar_name;
    private double latitude,longitude,Sourcelatitude,Sourcelongitude;
    double dist;



    public String getpharid(){return pharid;}
    public void setPharid(String string){this.pharid = string;}


    public String getpharaddress(){return phar_address;}
    public void setPharaddress(String string){this.phar_address = string;}

    public String getcontactnum(){return contact_num;}
    public void setcontactnum(String string){this.contact_num = string;}

    public String getphardistance(){return phar_distance;}
    public void setphardistance(String string){this.phar_distance = string;}

    public String getpharname() { return this.phar_name; }
    public void setpharname(String num) {this.phar_name = num; }

    public double getLatitude() { return this.latitude; }
    public void setLatitude(double num1) {this.latitude = num1; }

    public double getlongitude() { return this.longitude; }
    public void setlongitude(double num2) {this.longitude = num2; }

    public double getSourcelatitude() { return this.Sourcelatitude; }
    public void setSourcelatitude(double num3) {this.Sourcelatitude = num3; }

    public double getSourcelongitude() { return this.Sourcelongitude; }
    public void setSourcelongitude(double num4) {this.Sourcelongitude = num4; }

    public double getdistance() { return this.dist; }
    public void setdistance(double num5) {this.dist = num5; }



}
