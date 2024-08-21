package de.dynamicflash.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import de.dynamicflash.R;
import de.dynamicflash.adaptor.ProjectSwipeAdapter;
import de.dynamicflash.helper.RetrofitInstance;
import de.dynamicflash.model.PageResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 11/16/13
 * Time: 3:42 PM
 */
public class ProjectListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.viewpager, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Call<PageResult> project = RetrofitInstance.api().getChildPages("projects",1);
        project.enqueue(new Callback<PageResult>() {
            @Override
            public void onResponse(@NonNull Call<PageResult> call, @NonNull Response<PageResult> response) {
                ProjectSwipeAdapter adapter = new ProjectSwipeAdapter(getActivity(), response.body().getResults());
                View view = getView();
                assert view != null;
                ViewPager viewPager = view.findViewById(R.id.pagerID);
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<PageResult> call, @NonNull Throwable t) {
                call.cancel();
            }
        });
    }
}
