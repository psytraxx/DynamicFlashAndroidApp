package de.dynamicflash.activity;

import android.app.Activity;
import android.os.Bundle;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.adaptor.PhotoFullscreenSwipeAdapter;
import de.dynamicflash.databinding.ViewpagerBinding;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:31
 */
public class PhotoFullscreenSwipeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewpagerBinding binding = ViewpagerBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        String folder = getIntent().getStringExtra("folder");

        // Gridview adapter
        PhotoFullscreenSwipeAdapter adapter = new PhotoFullscreenSwipeAdapter(PhotoFullscreenSwipeActivity.this, ((GalleryApplication) getApplication()).getCurrentPhotos(), folder);

        int position = getIntent().getIntExtra("position", 0);

        binding.pager.setAdapter(adapter);
        binding.pager.setCurrentItem(position);

    }


}
