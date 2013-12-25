package de.dynamicflash.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.dynamicflash.R;
import de.dynamicflash.activity.PhotoGridJsonActivity;
import de.dynamicflash.adaptor.GalleryListAdapter;
import de.dynamicflash.helper.AppConstant;
import de.dynamicflash.loader.PageListLoader;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:16
 */
public class GalleryListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<List<Page>> {


    private List<Page> galleries = new ArrayList<Page>();
    private GalleryListAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();
        adapter = new GalleryListAdapter(getActivity(), R.layout.gallery_list,galleries);
        setListAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
    }

     public void onListItemClick(ListView l, View v, int position, long id) {
        Page selection = (Page) l.getItemAtPosition(position);

        Intent i = new Intent(getActivity(), PhotoGridJsonActivity.class);
         if (selection != null) {
             i.putExtra("folder", selection.getFolder());
             i.putExtra("title", selection.getTitle());
             startActivity(i);
         }

    }

    @Override
    public Loader<List<Page>> onCreateLoader(int i, Bundle bundle) {
        return new PageListLoader(getActivity().getApplicationContext(), AppConstant.GALLERY_LIST_JSON);
    }

    @Override
    public void onLoadFinished(Loader<List<Page>> listLoader, List<Page> galleries) {
       adapter.addAll(galleries);
    }

    @Override
    public void onLoaderReset(Loader<List<Page>> listLoader) {
        adapter.clear();
    }

}
