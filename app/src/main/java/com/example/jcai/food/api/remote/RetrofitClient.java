package com.example.jcai.food.api.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jcai on 3/14/18.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://food2fork.com/api/";
    public static final String api_key = "5ab2b60fffab514bf00ba73304e57af1";

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static F2FApiService getApiService(){
        if (retrofit==null) getClient();
        return retrofit.create(F2FApiService.class);
    }
}
