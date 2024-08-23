package de.dynamicflash.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import de.dynamicflash.helper.ApiImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoViewModel extends ViewModel {
    private final MutableLiveData<List<Photo>> photoLiveData;

    public PhotoViewModel() {
        photoLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Photo>> getPhotosByAlbumName(String folder) {
        fetchPhotosByAlbumName(folder);
        return photoLiveData;
    }

    private void fetchPhotosByAlbumName(String folder) {
        ApiImpl.instance().getPhotosByAlbumName(folder).enqueue(new Callback<List<Photo>>() {
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