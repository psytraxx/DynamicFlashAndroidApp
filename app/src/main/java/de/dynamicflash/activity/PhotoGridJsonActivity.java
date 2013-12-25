package de.dynamicflash.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.R;
import de.dynamicflash.adaptor.PhotoListAdapter;
import de.dynamicflash.loader.PhotoListLoader;
import de.dynamicflash.model.Photo;


/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:29
 */

public class PhotoGridJsonActivity extends Activity implements LoaderManager.LoaderCallbacks<List<Photo>> {

    private PhotoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_grid);

        adapter = new PhotoListAdapter(this, R.layout.photo_grid_item);

        GridView gridView = (GridView) findViewById(R.id.photoGridID);
        gridView.setOnItemClickListener(itemClickListener);

        // setting grid view adapter
        gridView.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);

    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            // on selecting grid view image
            // launch full screen activity
            Intent i = new Intent(getBaseContext(), PhotoFullscreenSwipeActivity.class);
            i.putExtra("position", position);
            startActivity(i);
        }
    };


    @Override
    public Loader<List<Photo>> onCreateLoader(int i, Bundle bundle) {
        return new PhotoListLoader(getApplicationContext(),getIntent().getStringExtra("folder"));
    }

    @Override
    public void onLoadFinished(Loader<List<Photo>> listLoader, List<Photo> photos) {
        //use it straight in swipe activity
        ((GalleryApplication)getApplication()).setCurrentPhotos(photos);
        adapter.addAll(photos);
    }

    @Override
    public void onLoaderReset(Loader<List<Photo>> listLoader) {
        adapter.clear();
    }

}