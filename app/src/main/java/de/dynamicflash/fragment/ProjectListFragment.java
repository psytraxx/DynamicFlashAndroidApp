package de.dynamicflash.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.R;
import de.dynamicflash.adaptor.ProjectSwipeAdapter;
import de.dynamicflash.helper.AppConstant;
import de.dynamicflash.helper.GsonRequest;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 11/16/13
 * Time: 3:42 PM
 */
public class ProjectListFragment extends Fragment {


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
        final String url = AppConstant.BASE_URL + AppConstant.PROJECT_LIST_JSON;

        Response.Listener<Page[]> listener = new Response.Listener<Page[]>() {

            @Override
            public void onResponse(Page[] response) {
                adapter = new ProjectSwipeAdapter(getActivity().getLayoutInflater(),response);
                ViewPager viewPager = (ViewPager) getView().findViewById(R.id.pagerID);
                viewPager.setAdapter(adapter);
            }
        };
        GsonRequest<Page[]> request = new GsonRequest<>(Request.Method.GET, url, listener, Page[].class, GalleryApplication.createErrorListener());
        ((GalleryApplication)getActivity().getApplication()).getReqQueue().add(request);
    }



}
