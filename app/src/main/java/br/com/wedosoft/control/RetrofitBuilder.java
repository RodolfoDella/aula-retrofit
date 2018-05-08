package br.com.wedosoft.control;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rodolfodellagiustina on 05/05/2018.
 */
public class RetrofitBuilder {

    public static Retrofit retrofit;
    static String baseUrl = "http://appteste.unibave.net:8080/api/";

    public static Retrofit getBuilder() {
        //Criando objeto de retrofit
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
