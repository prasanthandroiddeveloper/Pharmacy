package com.example.pharmacy;

import com.example.pharmacy.Pojo.DrugAddress;
import com.example.pharmacy.Pojo.LoginVerify;
import com.example.pharmacy.Pojo.MedicineSearch;
import com.example.pharmacy.Pojo.UserRegistration;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("medicine_details.php")
    Call<List<MedicineSearch>> getFoodieData();

    @FormUrlEncoded
    @POST("fetch_details.php")
    Call<List<MedicineSearch>> getData(@Field("mid") String id);

    @FormUrlEncoded
    @POST("fetch_address.php")
    Call<List<DrugAddress>> postmname(@Field("mname") String mname);

    @FormUrlEncoded
    @POST("login_reginsert.php")
    Call<UserRegistration> postdata(@Field("name") String Name,
                                    @Field("email") String Email,
                                    @Field("phoneno") String Phone,
                                    @Field("password") String Password);

    @FormUrlEncoded
    @POST("test.php")
    Call<List<LoginVerify>> verifydata(@Field("Email") String Email,
                                       @Field("Password") String Password);



}
