package com.example.pharmacy;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Badari on 19-07-2018.
 */

public class ApiClient {

    public static final String BASE_URL = "https://tripnetra.com/prasanth/androidphpfiles/Parmacyy/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
