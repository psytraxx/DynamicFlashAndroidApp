package de.dynamicflash.helper;

import static de.dynamicflash.helper.OkHttpClientInstance.httpClient;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.util.Date;
import java.util.List;

import de.dynamicflash.model.Page;
import de.dynamicflash.model.PageResult;
import de.dynamicflash.model.Photo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {

    private final Api apiService;
    private final MutableLiveData<List<Page>> pagesLiveData;
    private final MutableLiveData<List<Photo>> photoLiveData;

    public ApiRepository() {
        pagesLiveData = new MutableLiveData<>();
        photoLiveData = new MutableLiveData<>();

        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
        Gson gson = builder.create();


        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .client(httpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(Api.class);
    }

    public MutableLiveData<List<Page>> getChildPages(String pageid, Integer page) {
        fetchChildPages(pageid, page);
        return pagesLiveData;
    }

    private void fetchChildPages(String pageid, Integer page) {
        apiService.getChildPages(pageid,page).enqueue(new Callback<PageResult>() {
            @Override
            public void onResponse(@NonNull Call<PageResult> call, @NonNull Response<PageResult> response) {
                if (response.isSuccessful()) {
                    List<Page> results = response.body().getResults();
                    List<Page> currentPages = pagesLiveData.getValue();
                    if (currentPages != null) {
                        currentPages.addAll(results);
                        pagesLiveData.setValue(currentPages);
                    } else {
                        pagesLiveData.setValue(results);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PageResult> call, @NonNull Throwable t) {
                // Handle the error
            }
        });
    }

    public MutableLiveData<List<Photo>> getPhotosByAlbumName(String folder) {
        fetchPhotosByAlbumName(folder);
        return photoLiveData;
    }

    private void fetchPhotosByAlbumName(String folder) {
        apiService.getPhotosByAlbumName(folder).enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Photo>> call, @NonNull Response<List<Photo>> response) {
                if (response.isSuccessful()) {
                    List<Photo> results = response.body();
                    List<Photo> currentPhotos = photoLiveData.getValue();
                    if (currentPhotos != null) {
                        currentPhotos.addAll(results);
                        photoLiveData.setValue(currentPhotos);
                    } else {
                        photoLiveData.setValue(results);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Photo>> call, @NonNull Throwable t) {
                // Handle the error
            }
        });
    }
}
