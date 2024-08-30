package de.dynamicflash.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import de.dynamicflash.R;
import de.dynamicflash.adaptor.GalleryAdapter;
import de.dynamicflash.databinding.FragmentGalleryBinding;
import de.dynamicflash.helper.RecyclerItemClickListener;
import de.dynamicflash.model.Page;
import de.dynamicflash.model.PageViewModel;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:16
 */
public class GalleryFragment extends Fragment {

    private PageViewModel viewModel;
    private FragmentGalleryBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(PageViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView galleryList = binding.galleryList;

        GalleryAdapter adapter = new GalleryAdapter();
        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        galleryList.setLayoutManager(layoutManager);
        galleryList.setAdapter(adapter);

        // Observe LiveData from ViewModel
        viewModel.loadNextChildPages("photos").observe(getViewLifecycleOwner(), adapter::setPages);

        // Set up item click listener
        // Set up item click listener
        galleryList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), galleryList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Page selection = adapter.getItem(position);

                SlidingPaneLayout slidingPaneLayout = binding.slidingPaneLayout;
                if (!slidingPaneLayout.isOpen()) {
                    slidingPaneLayout.openPane();
                }

                // Launch fragment
                PhotoGridFragment fragment = new PhotoGridFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("folder", selection.getFolder());
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.photo_grid_container, fragment).commit();
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // Handle long item click if needed
            }
        }));

        // Set up scroll listener for pagination
        galleryList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                        firstVisibleItemPosition >= 0) {
                    viewModel.loadNextChildPages("photos").observe(getViewLifecycleOwner(), adapter::setPages);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

