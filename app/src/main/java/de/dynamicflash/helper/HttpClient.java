package de.dynamicflash.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.util.Arrays;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    private static HttpClient instance = null;
    private final Api myApi;

    public static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .followRedirects(true)
                .addInterceptor(new LoggingInterceptor())
                .protocols(Arrays.asList(Protocol.HTTP_1_1,Protocol.HTTP_2, Protocol.QUIC))
                .build();
    }

    private HttpClient() {
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
        Gson gson = builder.create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        myApi = retrofit.create(Api.class);
    }

    public static synchronized HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;
    }

    public Api getApi() {
        return myApi;
    }
}
