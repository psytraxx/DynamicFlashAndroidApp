package de.dynamicflash.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import de.dynamicflash.R;
import de.dynamicflash.activity.PhotoGridActivity;
import de.dynamicflash.adaptor.GalleryListAdapter;
import de.dynamicflash.helper.AppConstant;
import de.dynamicflash.helper.PageLoader;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:16
 */
public class GalleryListFragment extends ListFragment implements AbsListView.OnScrollListener, LoaderManager.LoaderCallbacks<Page[]> {

    private int currentPage = 0;

    @Override
    public void onStart() {
        super.onStart();
        setListAdapter(new GalleryListAdapter(getActivity(), new Page[0]));
        LoaderManager.getInstance(this).initLoader(0, null,this).forceLoad();

    }

    public void onListItemClick(ListView l, @NonNull View v, int position, long id) {
        Page selection = (Page) l.getItemAtPosition(position);

        if (selection != null) {

            View view = getActivity().findViewById(R.id.content_right);

            if (view == null) {
                // DisplayFragment (Fragment B) is not in the layout (handset layout),
                // so start DisplayActivity (Activity B)
                // and pass it the info about the selected item
                Intent i = new Intent(getActivity(), PhotoGridActivity.class);

                i.putExtra("folder", selection.getFolder());
                i.putExtra("title", selection.getTitle());
                startActivity(i);

            } else {
                //launch fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                PhotoGridFragment fragment = new PhotoGridFragment();
                Bundle bundle = new Bundle();
                bundle.putString("folder",selection.getFolder());
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.content_right, fragment).commit();
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView listView, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            // load 3 (4-1) items before we reach the end of the list
            if (listView.getLastVisiblePosition() >= listView.getCount() - 4) {
                currentPage++;
                LoaderManager.getInstance(this).restartLoader(0, null,this);
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {

    }

    @NonNull
    @Override
    public Loader<Page[]> onCreateLoader(int i, Bundle bundle) {
        return new PageLoader(getActivity(), currentPage, AppConstant.GALLERY_LIST_JSON);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Page[]> loader, Page[] data) {
        setListAdapter(new GalleryListAdapter(getActivity(), data));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Page[]> loader) {

    }
}

