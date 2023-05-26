package de.dynamicflash.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;

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

        LoaderManager.getInstance(this).initLoader(0, null,this).forceLoad();
    }


    @NonNull
    @Override
    public Loader<Page[]> onCreateLoader(int id, @Nullable Bundle args) {
        return new PageLoader(getActivity(), 0, AppConstant.PROJECT_LIST_JSON);
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<Page[]> loader, Page[] pages) {
        ProjectSwipeAdapter adapter = new ProjectSwipeAdapter(getActivity().getLayoutInflater(), pages);
        View view = getView();
        assert view != null;
        ViewPager viewPager = view.findViewById(R.id.pagerID);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<Page[]> loader) {

    }
}
