package de.dynamicflash.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.dynamicflash.R;
import de.dynamicflash.adaptor.ProjectSwipeAdapter;
import de.dynamicflash.helper.AppConstant;
import de.dynamicflash.loader.PageListLoader;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 11/16/13
 * Time: 3:42 PM
 */
public class ProjectListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Page>> {


    private ProjectSwipeAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.viewpager, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<Page>> onCreateLoader(int i, Bundle bundle) {
        return new PageListLoader(getActivity().getApplicationContext(),AppConstant.PROJECT_LIST_JSON);
    }

    @Override
    public void onLoadFinished(Loader<List<Page>> listLoader, List<Page> pages) {
        adapter = new ProjectSwipeAdapter(getActivity().getLayoutInflater(),pages);
        ViewPager viewPager = (ViewPager)  getView().findViewById(R.id.pagerID);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Page>> listLoader) {
        adapter = null;
    }


}
