package de.dynamicflash.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.R;
import de.dynamicflash.adaptor.PhotoFullscreenSwipeAdapter;

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

        setContentView(R.layout.viewpager);


        ViewPager viewPager = (ViewPager) findViewById(R.id.pagerID);

        // Gridview adapter
        PhotoFullscreenSwipeAdapter adapter = new PhotoFullscreenSwipeAdapter(PhotoFullscreenSwipeActivity.this, ((GalleryApplication)getApplication()).getCurrentPhotos());

        int postion = getIntent().getIntExtra("position", 0);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(postion);

    }


}
