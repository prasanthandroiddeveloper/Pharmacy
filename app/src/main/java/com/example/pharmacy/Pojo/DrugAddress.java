package com.example.pharmacy.Pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class DrugAddress {

    @SerializedName("phar_id")
    @Expose
    private String pharId;

    @SerializedName("phar_address")
    @Expose
    private String pharAddress;

    @SerializedName("contact_num")
    @Expose
    private String contactNum;

    @SerializedName("phar_distance")
    @Expose
    private String pharDistance;

    @SerializedName("phar_name")
    @Expose
    private String pharName;


    @SerializedName("latitude")
    @Expose
    private float latitude;

    @SerializedName("longitude")
    @Expose
    private float longitude;


    @SerializedName("sourcelatitude")
    @Expose
    private float Sourcelatitude;

    @SerializedName("sourcelongitude")
    @Expose
    private float Sourcelongitude;

    public String getPharId() {
        return pharId;
    }

    public void setPharId(String pharId) {
        this.pharId = pharId;
    }

    public String getPharAddress() {
        return pharAddress;
    }

    public void setPharAddress(String pharAddress) {
        this.pharAddress = pharAddress;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getPharDistance() {
        return pharDistance;
    }

    public void setPharDistance(String pharDistance) {
        this.pharDistance = pharDistance;
    }

    public String getPharName() {
        return pharName;
    }

    public void setPharName(String pharName) {
        this.pharName = pharName;
    }


    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }



    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }


    public float getSourcelatitude() {
        return Sourcelatitude;
    }

    public float getSourcelongitude(){
        return Sourcelongitude;
    }

}