package com.example.pharmacy.Pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class MedicineSearch {
    @SerializedName("medicine_id")
    @Expose
    private String medicineId;
    @SerializedName("medicine_name")
    @Expose
    private String medicineName;
    @SerializedName("medicine_usage")
    @Expose
    private String medicineUsage;
    @SerializedName("precautions")
    @Expose
    private String precautions;
    @SerializedName("dosage")
    @Expose
    private String dosage;
    @SerializedName("storage")
    @Expose
    private String storage;
    @SerializedName("side_effects")
    @Expose
    private String sideEffects;
    @SerializedName("medicine_mg")
    @Expose
    private String medicineMg;
    @SerializedName("how_to_use")
    @Expose
    private String howToUse;
    @SerializedName("phar_id")
    @Expose
    private String pharId;

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineUsage() {
        return medicineUsage;
    }

    public void setMedicineUsage(String medicineUsage) {
        this.medicineUsage = medicineUsage;
    }

    public String getPrecautions() {
        return precautions;
    }

    public void setPrecautions(String precautions) {
        this.precautions = precautions;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getMedicineMg() {
        return medicineMg;
    }

    public void setMedicineMg(String medicineMg) {
        this.medicineMg = medicineMg;
    }

    public String getHowToUse() {
        return howToUse;
    }

    public void setHowToUse(String howToUse) {
        this.howToUse = howToUse;
    }

    public String getPharId() {
        return pharId;
    }

    public void setPharId(String pharId) {
        this.pharId = pharId;
    }
}
