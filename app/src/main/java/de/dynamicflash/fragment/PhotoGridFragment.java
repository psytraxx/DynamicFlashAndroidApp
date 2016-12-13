package de.dynamicflash.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.R;
import de.dynamicflash.activity.PhotoFullscreenSwipeActivity;
import de.dynamicflash.adaptor.PhotoListAdapter;
import de.dynamicflash.helper.PhotoLoader;
import de.dynamicflash.model.Photo;


/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:29
 */

public class PhotoGridFragment extends Fragment implements LoaderManager.LoaderCallbacks<Photo[]> {

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

        getLoaderManager().initLoader(0, null,this).forceLoad();

    }

    private final AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            // on selecting grid view image
            // launch full screen activity
            Intent i = new Intent(getActivity().getBaseContext(), PhotoFullscreenSwipeActivity.class);
            i.putExtra("position", position);
            startActivity(i);
        }
    };

    @Override
    public Loader<Photo[]> onCreateLoader(int i, Bundle bundle) {
        return new PhotoLoader(getActivity(), getArguments().getString("folder"));
    }

    @Override
    public void onLoadFinished(Loader<Photo[]> loader, Photo[] photos) {
        final GalleryApplication application = (GalleryApplication) getActivity().getApplication();
        application.setCurrentPhotos(photos);
        adapter.addAll(photos);

    }

    @Override
    public void onLoaderReset(Loader<Photo[]> loader) {

    }
}