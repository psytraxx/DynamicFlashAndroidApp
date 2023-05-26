package de.dynamicflash.helper;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.dynamicflash.model.Page;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by eric on 17/12/16. - all rights reserved
 */
public class PageLoader extends AsyncTaskLoader<Page[]> {

    private static final int MAX_RESULTS = 20;
    private final int pageNumber;
    private final String path;

    public PageLoader(Context context, int pageNumber, String path) {
        super(context);
        this.pageNumber = pageNumber;
        this.path = path;
    }

    @Override
    public Page[] loadInBackground() {
        final String url = AppConstant.BASE_URL + String.format(path, pageNumber, MAX_RESULTS);

        List<Protocol> protocols = new ArrayList<>();
        protocols.add(Protocol.HTTP_2);
        protocols.add(Protocol.HTTP_1_1);

        OkHttpClient client = new OkHttpClient.Builder()
                .protocols(protocols)
                .addInterceptor(new LoggingInterceptor())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (
                Response response = client.newCall(request).execute()) {
            // Creates the json object which will manage the information received
            GsonBuilder builder = new GsonBuilder();

            // Register an adapter to manage the date types as long values
            builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));

            Gson gson = builder.create();
            assert response.body() != null;
            return gson.fromJson(response.body().charStream(), Page[].class);
        } catch (Exception ex) {
            return null;
        }

    }
}
