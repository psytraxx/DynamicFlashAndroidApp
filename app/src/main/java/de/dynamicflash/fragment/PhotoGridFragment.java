package de.dynamicflash.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.activity.PhotoSwipeActivity;
import de.dynamicflash.adaptor.PhotoGridAdapter;
import de.dynamicflash.databinding.FragmentPhotoGridBinding;
import de.dynamicflash.model.PhotoViewModel;


/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:29
 */

public class PhotoGridFragment extends Fragment implements PhotoGridAdapter.ItemClickListener {

    private String folder;
    private FragmentPhotoGridBinding binding;
    private PhotoViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(PhotoViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPhotoGridBinding.inflate(inflater, container, false);
        folder = getArguments().getString("folder");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setup recyclerview
        RecyclerView recyclerView = binding.photoGrid;
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        PhotoGridAdapter adapter = new PhotoGridAdapter(folder);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        // Observe ViewModel data changes
        viewModel.getPhotosByAlbumName(folder).observe(getViewLifecycleOwner(), photos -> {
            final GalleryApplication application = (GalleryApplication) getActivity().getApplication();
            // Update RecyclerView when data changes
            application.setCurrentPhotos(photos);
            adapter.addAll(photos);
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent(getContext(), PhotoSwipeActivity.class);
        i.putExtra("position", position);
        i.putExtra("folder", folder);
        startActivity(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        viewModel = null;
    }
}