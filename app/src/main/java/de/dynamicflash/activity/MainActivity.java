package de.dynamicflash.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.dynamicflash.R;
import de.dynamicflash.adaptor.DrawerAdapter;
import de.dynamicflash.fragment.GalleryFragment;
import de.dynamicflash.fragment.ProjectFragment;
import de.dynamicflash.helper.RecyclerItemClickListener;

/**
 * Created by eric on 01/08/2014.
 * psytraxx@gmail.com
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView drawerRecyclerView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence drawerTitle;
    private CharSequence title;

    private String[] drawerEntries = new String[0];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerEntries = new String[]{getString(R.string.title_photos), getString(R.string.title_projects)};

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerRecyclerView = findViewById(R.id.left_drawer);
        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the adapter for the list view
        drawerRecyclerView.setAdapter(new DrawerAdapter(drawerEntries));
        // Set the list's click listener
        drawerRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, drawerRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectItem(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));

        title = drawerTitle = getTitle();

        // enable ActionBar app icon to behave as action to toggle nav drawer
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                actionBar.setTitle(title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                actionBar.setTitle(drawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new GalleryFragment();
                break;
            case 1:
                fragment = new ProjectFragment();
                break;
            case 2:

        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            // update selected item and title, then close the drawer
            drawerRecyclerView.setSelected(true);
            setTitle(drawerEntries[position]);
            drawerLayout.closeDrawer(drawerRecyclerView);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(this.title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}
