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

    private int currentPage = 1;
    private GalleryAdapter adapter;
    private PageViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView galleryList = view.findViewById(R.id.gallery_list);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(PageViewModel.class);
        // Observe LiveData from ViewModel
        viewModel.getChildPages("photos", currentPage).observe(getViewLifecycleOwner(), pages -> adapter.setPages(pages));

        adapter = new GalleryAdapter();

        // Set up RecyclerView
        galleryList.setLayoutManager(new LinearLayoutManager(getContext()));
        galleryList.setAdapter(adapter);

        // Set up item click listener
        // Set up item click listener
        galleryList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), galleryList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Page selection = adapter.getItem(position);

                SlidingPaneLayout slidingPaneLayout = requireActivity().findViewById(R.id.sliding_pane_layout);
                if (slidingPaneLayout != null && !slidingPaneLayout.isOpen()) {
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

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastVisibleItemPosition() >= adapter.getItemCount() - 4) {
                    currentPage++;
                    viewModel.getChildPages("photos", currentPage).observe(getViewLifecycleOwner(), pages -> adapter.setPages(pages));
                }
            }
        });
    }
}

