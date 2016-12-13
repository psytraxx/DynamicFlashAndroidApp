package de.dynamicflash.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.dynamicflash.R;
import de.dynamicflash.adaptor.ProjectSwipeAdapter;
import de.dynamicflash.helper.AppConstant;
import de.dynamicflash.helper.PageLoader;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 11/16/13
 * Time: 3:42 PM
 */
public class ProjectListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Page[]> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.viewpager, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(0, null,this).forceLoad();
    }


    @Override
    public Loader<Page[]> onCreateLoader(int i, Bundle bundle) {
        return new PageLoader(getActivity(), 0, AppConstant.PROJECT_LIST_JSON);
    }

    @Override
    public void onLoadFinished(Loader<Page[]> loader, Page[] pages) {
        ProjectSwipeAdapter adapter = new ProjectSwipeAdapter(getActivity().getLayoutInflater(), pages);
        View view = getView();
        assert view != null;
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pagerID);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Page[]> loader) {

    }
}
