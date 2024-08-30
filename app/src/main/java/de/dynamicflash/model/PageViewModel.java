package de.dynamicflash.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import de.dynamicflash.helper.ApiImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PageViewModel extends ViewModel {

    private int currentPage = 1;

    private final MutableLiveData<List<Page>> pages = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private boolean hasMorePages = true;

    public LiveData<List<Page>> loadNextChildPages(String pageid) {
        fetchChildPages(pageid, currentPage);
        currentPage++;
        return pages;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private void fetchChildPages(String pageid, Integer page) {

        if (Boolean.TRUE.equals(isLoading.getValue()) || !hasMorePages) return;

        isLoading.setValue(true);

        ApiImpl.instance().getChildPages(pageid, page).enqueue(new Callback<PageResult>() {
            @Override
            public void onResponse(@NonNull Call<PageResult> call, @NonNull Response<PageResult> response) {
                if (response.isSuccessful()) {
                    List<Page> results = response.body().getResults();
                    List<Page> currentPages = pages.getValue();
                    if (currentPages != null) {
                        currentPages.addAll(results);
                        pages.postValue(currentPages);
                    }
                    hasMorePages = !results.isEmpty();
                    isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PageResult> call, @NonNull Throwable t) {
                // Handle the error
            }
        });
    }


}