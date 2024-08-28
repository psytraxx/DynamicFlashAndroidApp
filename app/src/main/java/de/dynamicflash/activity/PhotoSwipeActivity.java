package de.dynamicflash.activity;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.adaptor.PhotoSwipeAdapter;
import de.dynamicflash.databinding.ViewpagerBinding;
import de.dynamicflash.model.Photo;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:31
 */
public class PhotoSwipeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewpagerBinding binding = ViewpagerBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        String folder = getIntent().getStringExtra("folder");

        // Gridview adapter
        List<Photo> photos = ((GalleryApplication) getApplication()).getCurrentPhotos();
        PhotoSwipeAdapter adapter = new PhotoSwipeAdapter(photos, folder);

        int position = getIntent().getIntExtra("position", 0);

        binding.pager.setAdapter(adapter);
        binding.pager.setCurrentItem(position);

    }


}
