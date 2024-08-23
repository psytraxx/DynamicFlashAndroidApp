package de.dynamicflash.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiImpl {

    private static Api api;

    public static Api instance() {
        if (api == null) {
            GsonBuilder builder = new GsonBuilder();

            // Register an adapter to manage the date types as long values
            builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
            Gson gson = builder.create();


            Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                    .client(OkHttpClientImpl.instance())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            api = retrofit.create(Api.class);

        }
        return api;
    }
}
