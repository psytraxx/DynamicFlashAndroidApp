package de.dynamicflash.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.R;
import de.dynamicflash.activity.PhotoSwipeActivity;
import de.dynamicflash.adaptor.PhotoGridAdapter;
import de.dynamicflash.model.PhotoViewModel;


/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:29
 */

public class PhotoGridFragment extends Fragment {

    private final AdapterView.OnItemClickListener itemClickListener = (parent, v, position, id) -> {
        // on selecting grid view image
        // launch full screen activity
        Intent i = new Intent(parent.getContext(), PhotoSwipeActivity.class);
        i.putExtra("position", position);
        i.putExtra("folder", getArguments().getString("folder"));
        startActivity(i);
    };
    private PhotoGridAdapter adapter;
    private PhotoViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(PhotoViewModel.class);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_grid, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        GridView view = (GridView) getView();
        if (view == null) {
            return;
        }
        view.setOnItemClickListener(itemClickListener);

        String folder = getArguments().getString("folder");
        // setting grid view adapter
        adapter = new PhotoGridAdapter(getActivity(), folder);
        view.setAdapter(adapter);

        viewModel.getPhotosByAlbumName(folder).observe(this, photos -> {
            final GalleryApplication application = (GalleryApplication) getActivity().getApplication();
            application.setCurrentPhotos(photos);
            adapter.addAll(photos);
        });
    }
}