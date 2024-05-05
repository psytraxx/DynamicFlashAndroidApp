package de.dynamicflash.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.R;
import de.dynamicflash.activity.PhotoFullscreenSwipeActivity;
import de.dynamicflash.adaptor.PhotoListAdapter;
import de.dynamicflash.helper.RetrofitInstance;
import de.dynamicflash.model.Photo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        Intent i = new Intent(getActivity().getBaseContext(), PhotoFullscreenSwipeActivity.class);
        i.putExtra("position", position);
        startActivity(i);
    };
    private PhotoListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.photo_grid, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        GridView view = (GridView) getView();
        if (view == null) {
            return;
        }
        view.setOnItemClickListener(itemClickListener);

        // setting grid view adapter
        adapter = new PhotoListAdapter(getActivity());
        view.setAdapter(adapter);

        Call<Photo[]> album = RetrofitInstance.api().getAlbum(getArguments().getString("folder"));

        album.enqueue(new Callback<Photo[]>() {
            @Override
            public void onResponse(Call<Photo[]> call, Response<Photo[]> response) {
                final GalleryApplication application = (GalleryApplication) getActivity().getApplication();
                application.setCurrentPhotos(response.body());
                adapter.addAll(response.body());
            }

            @Override
            public void onFailure(Call<Photo[]> call, Throwable t) {
                call.cancel();
            }
        });

    }
}