package de.dynamicflash.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.R;
import de.dynamicflash.adaptor.PhotoListAdapter;
import de.dynamicflash.helper.AppConstant;
import de.dynamicflash.helper.GsonRequest;
import de.dynamicflash.model.PhotoList;


/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:29
 */

public class PhotoGridJsonActivity extends Activity  {

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

        final String url = AppConstant.BASE_URL + String.format(AppConstant.ALBUM_LIST_JSON, getIntent().getStringExtra("folder"));

        Response.Listener<PhotoList> listener = new Response.Listener<PhotoList>() {

            @Override
            public void onResponse(PhotoList response) {
                adapter.addAll(response);
                adapter.notifyDataSetChanged();
                ((GalleryApplication)getApplication()).setCurrentPhotos(response);
            }
        };
        GsonRequest<PhotoList> request = new GsonRequest<PhotoList>(Request.Method.GET,url, listener, PhotoList.class, GalleryApplication.createErrorListener());
        ((GalleryApplication)getApplication()).getReqQueue().add(request);


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


}