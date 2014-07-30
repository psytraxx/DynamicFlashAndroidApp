package de.dynamicflash.fragment;


import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.ArrayList;
import java.util.List;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.R;
import de.dynamicflash.activity.PhotoGridJsonActivity;
import de.dynamicflash.adaptor.GalleryListAdapter;
import de.dynamicflash.helper.AppConstant;
import de.dynamicflash.helper.GsonRequest;
import de.dynamicflash.model.Page;
import de.dynamicflash.model.PageList;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:16
 */
public class GalleryListFragment extends ListFragment {


    private List<Page> galleries = new ArrayList<Page>();
    private GalleryListAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();
        adapter = new GalleryListAdapter(getActivity(), R.layout.gallery_list,galleries);
        setListAdapter(adapter);
        final String url = AppConstant.BASE_URL + AppConstant.GALLERY_LIST_JSON;

        Response.Listener<PageList> listener = new Response.Listener<PageList>() {

            @Override
            public void onResponse(PageList response) {
                adapter.addAll(response);
                adapter.notifyDataSetChanged();
            }
        };
        GsonRequest<PageList> request = new GsonRequest<PageList>(Request.Method.GET,url, listener, PageList.class, GalleryApplication.createErrorListener());
        ((GalleryApplication)getActivity().getApplication()).getReqQueue().add(request);
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

}
