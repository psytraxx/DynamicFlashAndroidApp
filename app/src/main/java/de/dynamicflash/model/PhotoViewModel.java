package de.dynamicflash.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import de.dynamicflash.helper.ApiRepository;

public class PhotoViewModel extends ViewModel {

    private final ApiRepository repository;

    public PhotoViewModel() {
        repository = new ApiRepository();
    }

    public LiveData<List<Photo>> getPhotosByAlbumName(String folder) {
        return repository.getPhotosByAlbumName(folder);
    }
}