package de.dynamicflash.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.R;
import de.dynamicflash.activity.PhotoFullscreenSwipeActivity;
import de.dynamicflash.adaptor.PhotoListAdapter;
import de.dynamicflash.helper.AppConstant;
import de.dynamicflash.helper.GsonRequest;
import de.dynamicflash.model.Photo;


/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:29
 */

public class PhotoGridFragment extends Fragment {

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

        adapter = new PhotoListAdapter(getActivity(), R.layout.photo_grid_item);

        GridView view = (GridView) getView();
        if (view == null) {
            return;
        }
        view.setOnItemClickListener(itemClickListener);

        // setting grid view adapter
        view.setAdapter(adapter);

        final String url = AppConstant.BASE_URL + String.format(AppConstant.ALBUM_LIST_JSON, getArguments().getString("folder"));

        final GalleryApplication application = (GalleryApplication) getActivity().getApplication();

        Response.Listener<Photo[]> listener = new Response.Listener<Photo[]>() {

            @Override
            public void onResponse(Photo[] response) {
                adapter.addAll(response);
                adapter.notifyDataSetChanged();
                application.setCurrentPhotos(response);
            }
        };
        GsonRequest<Photo[]> request = new GsonRequest<>(Request.Method.GET,url, listener, Photo[].class, GalleryApplication.createErrorListener());
        application.getReqQueue().add(request);

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

}