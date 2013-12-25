package de.dynamicflash.adaptor;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import de.dynamicflash.R;
import de.dynamicflash.fragment.GalleryListFragment;
import de.dynamicflash.fragment.ProjectListFragment;

public class SectionsPagerAdaptor extends FragmentPagerAdapter {

    private final Context context;

    public SectionsPagerAdaptor(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a DummySectionFragment (defined as a static inner class
        // below) with the page number as its lone argument.
        Fragment fragment;
        if (position == 0) {
            fragment = new GalleryListFragment();
        } else {
            fragment = new ProjectListFragment();
        }
        return fragment;
    }
    // END_INCLUDE (fragment_pager_adapter_getitem)

    // BEGIN_INCLUDE (fragment_pager_adapter_getcount)

    /**
     * Get number of pages the {@link android.support.v4.view.ViewPager} should render.
     *
     * @return Number of fragments to be rendered as pages.
     */
    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }
    // END_INCLUDE (fragment_pager_adapter_getcount)

    // BEGIN_INCLUDE (fragment_pager_adapter_getpagetitle)

    /**
     * Get title for each of the pages. This will be displayed on each of the tabs.
     *
     * @param position Page to fetch title for.
     * @return Title for specified page.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return context.getString(R.string.title_section2).toUpperCase(l);
        }
        return null;
    }
    // END_INCLUDE (fragment_pager_adapter_getpagetitle)
}
