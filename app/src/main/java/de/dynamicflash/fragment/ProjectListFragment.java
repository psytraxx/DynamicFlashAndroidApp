package de.dynamicflash.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import de.dynamicflash.R;
import de.dynamicflash.adaptor.ProjectSwipeAdapter;
import de.dynamicflash.model.PageViewModel;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 11/16/13
 * Time: 3:42 PM
 */
public class ProjectListFragment extends Fragment {

    private PageViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(PageViewModel.class);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.viewpager, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Observe LiveData from ViewModel
        viewModel.getChildPages("projects", 1).observe(this, pages -> {
            ProjectSwipeAdapter adapter = new ProjectSwipeAdapter(getActivity(), new ArrayList<>(pages));
            View view = getView();
            assert view != null;
            ViewPager viewPager = view.findViewById(R.id.pagerID);
            viewPager.setAdapter(adapter);
        });

    }
}
