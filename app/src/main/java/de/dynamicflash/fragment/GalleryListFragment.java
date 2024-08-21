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

import de.dynamicflash.R;
import de.dynamicflash.activity.PhotoGridActivity;
import de.dynamicflash.adaptor.GalleryListAdapter;
import de.dynamicflash.helper.RetrofitInstance;
import de.dynamicflash.model.Page;
import de.dynamicflash.model.PageResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:16
 */
public class GalleryListFragment extends ListFragment implements AbsListView.OnScrollListener {

    private int currentPage = 1;
    private GalleryListAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();
        setListAdapter(new GalleryListAdapter(getActivity(), new Page[0]));

        loadCurrentPage();
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
                bundle.putString("folder", selection.getFolder());
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
                loadCurrentPage();
            }
        }
    }

    private void loadCurrentPage() {
        Call<PageResult> project = RetrofitInstance.api().getChildPages("photos", currentPage);
        project.enqueue(new Callback<PageResult>() {
            @Override
            public void onResponse(@NonNull Call<PageResult> call, @NonNull Response<PageResult> response) {
                if (adapter == null) {
                    adapter = new GalleryListAdapter(getActivity(), response.body().getResults());
                    setListAdapter(adapter);
                } else {
                    adapter.addItems(response.body().getResults());
                }

            }

            @Override
            public void onFailure(@NonNull Call<PageResult> call, @NonNull Throwable t) {
                call.cancel();
            }
        });
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {

    }
}

