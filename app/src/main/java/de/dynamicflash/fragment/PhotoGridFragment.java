package de.dynamicflash.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.activity.PhotoFullscreenSwipeActivity;
import de.dynamicflash.adaptor.PhotoListAdapter;
import de.dynamicflash.databinding.PhotoGridBinding;
import de.dynamicflash.model.PhotoViewModel;


/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:29
 */

public class PhotoGridFragment extends Fragment {

    private PhotoViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(PhotoViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        PhotoGridBinding binding = PhotoGridBinding.inflate(inflater, container, false);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView gridView = (GridView) view;
        gridView.setOnItemClickListener(itemClickListener);

        String folder = getArguments().getString("folder");
        // setting grid view adapter
        PhotoListAdapter adapter = new PhotoListAdapter(getActivity(), folder);
        gridView.setAdapter(adapter);

        viewModel.getPhotosByAlbumName(folder).observe(getViewLifecycleOwner(), photos -> {
            final GalleryApplication application = (GalleryApplication) getActivity().getApplication();
            application.setCurrentPhotos(photos);
            adapter.addAll(photos);
        });
    }

    private final AdapterView.OnItemClickListener itemClickListener = (parent, v, position, id) -> {
        // on selecting grid view image
        // launch full screen activity
        Intent i = new Intent(getActivity().getBaseContext(), PhotoFullscreenSwipeActivity.class);
        i.putExtra("position", position);
        i.putExtra("folder", getArguments().getString("folder"));
        startActivity(i);
    };
}