package de.dynamicflash.fragment;


import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.ArrayList;
import java.util.List;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.R;
import de.dynamicflash.activity.PhotoGridActivity;
import de.dynamicflash.adaptor.GalleryListAdapter;
import de.dynamicflash.helper.AppConstant;
import de.dynamicflash.helper.GsonRequest;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:16
 */
public class GalleryListFragment extends ListFragment  implements AbsListView.OnScrollListener {


    private static final int MAX_RESULTS = 20;
    private final List<Page> galleries = new ArrayList<>();
    private GalleryListAdapter adapter;
    private int currentPage = 0;

    @Override
    public void onStart() {
        super.onStart();
        adapter = new GalleryListAdapter(getActivity(), R.layout.gallery_list, galleries);
        setListAdapter(adapter);

        loadElements(currentPage);

    }

    private void loadElements(int currentPage) {
        final String url = AppConstant.BASE_URL + String.format(AppConstant.GALLERY_LIST_JSON, currentPage, MAX_RESULTS);
        Log.d(GalleryApplication.TAG,url);
        Response.Listener<Page[]> listener = new Response.Listener<Page[]>() {

            @Override
            public void onResponse(Page[] response) {
                adapter.addAll(response);
                adapter.notifyDataSetChanged();
            }
        };
        GsonRequest<Page[]> request = new GsonRequest<>(Request.Method.GET, url, listener, Page[].class, GalleryApplication.createErrorListener());
        ((GalleryApplication) getActivity().getApplication()).getReqQueue().add(request);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Page selection = (Page) l.getItemAtPosition(position);


        if (selection != null) {

            View view =  getActivity().findViewById(R.id.content_right);

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
    public void onActivityCreated (Bundle savedInstanceState){
        // Always call the superclass so it can save the view hierarchy state
        super.onCreate(savedInstanceState);

        getListView().setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView listView, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            // load 3 (4-1) items before we reach the end of the list
            if (listView.getLastVisiblePosition() >= listView.getCount() - 4 ) {
                currentPage++;
                //load more list items:
                loadElements(currentPage);
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {

    }

}
