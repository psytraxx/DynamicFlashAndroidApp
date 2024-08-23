package de.dynamicflash.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import de.dynamicflash.adaptor.ProjectSwipeAdapter;
import de.dynamicflash.databinding.ViewpagerBinding;
import de.dynamicflash.model.PageViewModel;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 11/16/13
 * Time: 3:42 PM
 */
public class ProjectFragment extends Fragment {

    private PageViewModel viewModel;
    private ViewpagerBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(PageViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ViewpagerBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observe LiveData from ViewModel
        viewModel.getChildPages("projects", 1).observe(getViewLifecycleOwner(), pages -> {
            ProjectSwipeAdapter adapter = new ProjectSwipeAdapter(getActivity(), new ArrayList<>(pages));
            binding.pager.setAdapter(adapter);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
