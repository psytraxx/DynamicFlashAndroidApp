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

public class PageViewModel extends ViewModel {

    private final MutableLiveData<List<Page>> pagesLiveData;

    public PageViewModel() {
        pagesLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Page>> getChildPages(String pageid, Integer page) {
        fetchChildPages(pageid, page);
        return pagesLiveData;
    }

    private void fetchChildPages(String pageid, Integer page) {
        ApiImpl.instance().getChildPages(pageid, page).enqueue(new Callback<PageResult>() {
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


}