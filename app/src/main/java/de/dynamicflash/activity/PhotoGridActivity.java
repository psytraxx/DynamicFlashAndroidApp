package de.dynamicflash.activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import de.dynamicflash.R;
import de.dynamicflash.fragment.PhotoGridFragment;


/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:29
 */

public class PhotoGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.photo_grid_holder);

        if (savedInstanceState == null) {
            PhotoGridFragment fragment = new PhotoGridFragment();

            String folder = getIntent().getStringExtra("folder");
            Bundle bundle = new Bundle();
            bundle.putString("folder",folder);
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.grid_holder, fragment).commit();

        }

    }
}