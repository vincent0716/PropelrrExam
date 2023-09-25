package com.vmcruz.propelrrexam;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIClient {
    public static Retrofit getClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return new Retrofit.Builder()
                .baseUrl("https://run.mocky.io/v3/")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }


}
