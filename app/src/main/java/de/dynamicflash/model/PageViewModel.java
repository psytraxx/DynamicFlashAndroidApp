package de.dynamicflash.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import de.dynamicflash.helper.ApiRepository;

public class PageViewModel extends ViewModel {

    private final ApiRepository repository;

    public PageViewModel() {
        repository = new ApiRepository();
    }

    public LiveData<List<Page>> getChildPages(String pageid, Integer page) {
        return repository.getChildPages(pageid, page);
    }
}