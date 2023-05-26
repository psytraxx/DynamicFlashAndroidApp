package de.dynamicflash.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import de.dynamicflash.R;
import de.dynamicflash.adaptor.ProjectSwipeAdapter;
import de.dynamicflash.helper.HttpClient;
import de.dynamicflash.model.Page;
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

        Call<Page[]> project = HttpClient.getInstance().getApi().getProjects();
        project.enqueue(new Callback<Page[]>() {
            @Override
            public void onResponse(Call<Page[]> call, Response<Page[]> response) {
                ProjectSwipeAdapter adapter = new ProjectSwipeAdapter(getActivity(), response.body());
                View view = getView();
                assert view != null;
                ViewPager viewPager = view.findViewById(R.id.pagerID);
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Page[]> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
