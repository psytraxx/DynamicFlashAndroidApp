package de.dynamicflash.fragment;


import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

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
public class GalleryListFragment extends ListFragment implements AbsListView.OnScrollListener,LoaderManager.LoaderCallbacks<Page[]> {

    private int currentPage = 0;

    @Override
    public void onStart() {
        super.onStart();
        setListAdapter(new GalleryListAdapter(getActivity(), new Page[0]));
        getLoaderManager().initLoader(0, null,this).forceLoad();

    }

    public void onListItemClick(ListView l, View v, int position, long id) {
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
                FragmentManager fragmentManager = getFragmentManager();
                PhotoGridFragment fragment = new PhotoGridFragment();
                Bundle bundle = new Bundle();
                bundle.putString("folder",selection.getFolder());
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.content_right, fragment).commit();
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onActivityCreated(savedInstanceState);

        getListView().setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView listView, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            // load 3 (4-1) items before we reach the end of the list
            if (listView.getLastVisiblePosition() >= listView.getCount() - 4) {
                currentPage++;
                getLoaderManager().restartLoader(0, null,this);
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {

    }


    @Override
    public Loader<Page[]> onCreateLoader(int i, Bundle bundle) {
        return new PageLoader(getActivity(), currentPage, AppConstant.GALLERY_LIST_JSON);
    }

    @Override
    public void onLoadFinished(Loader<Page[]> loader, Page[] pages) {
        setListAdapter(new GalleryListAdapter(getActivity(), pages));
    }

    @Override
    public void onLoaderReset(Loader<Page[]> loader) {

    }
}

