package de.dynamicflash.helper;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import de.dynamicflash.model.Photo;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by eric on 17/12/16. - all rights reserved
 */
public class PhotoLoader extends AsyncTaskLoader<Photo[]> {

    private final String folder;
    private final String path;

    public PhotoLoader(Context context, String folder) {
        super(context);
        this.folder = folder;
        this.path = AppConstant.ALBUM_LIST_JSON;
    }

    @Override
    public Photo[] loadInBackground() {
        final String url = AppConstant.BASE_URL + String.format(path, folder);

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

        try {
            Response response = client.newCall(request).execute();
            Gson gson = new Gson();
            return gson.fromJson(response.body().charStream(), Photo[].class);
        } catch (Exception ex) {
            return null;
        }

    }
}
