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
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import de.dynamicflash.activity.PhotoGridActivity;
import de.dynamicflash.adaptor.GalleryListAdapter;
import de.dynamicflash.databinding.DrawerBinding;
import de.dynamicflash.model.Page;
import de.dynamicflash.model.PageViewModel;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:16
 */
public class GalleryListFragment extends ListFragment implements AbsListView.OnScrollListener {

    private int currentPage = 1;
    private GalleryListAdapter adapter;
    private PageViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(PageViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new GalleryListAdapter(getContext(), new ArrayList<>());
        setListAdapter(adapter);

        // Observe LiveData from ViewModel
        viewModel.getChildPages("photos", currentPage).observe(getViewLifecycleOwner(), this::updatePageList);

        // Set up scroll listener
        getListView().setOnScrollListener(this);
    }

    public void onListItemClick(ListView l, @NonNull View v, int position, long id) {
        Page selection = (Page) l.getItemAtPosition(position);

        if (selection != null) {
            DrawerBinding binding = DrawerBinding.inflate(getLayoutInflater());

            View view = binding.contentRight;

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
                fragmentManager.beginTransaction().replace(binding.contentRight.getId(), fragment).commit();
            }
        }
    }

    private void updatePageList(List<Page> pages) {
        adapter.clear();
        adapter.addAll(pages);
    }

    @Override
    public void onScrollStateChanged(AbsListView listView, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            // load 3 (4-1) items before we reach the end of the list
            if (listView.getLastVisiblePosition() >= listView.getCount() - 4) {
                currentPage++;
                viewModel.getChildPages("photos", currentPage).observe(getViewLifecycleOwner(), this::updatePageList);
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {

    }
}

