package de.dynamicflash.loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.dynamicflash.GalleryApplication;
import de.dynamicflash.helper.AppConstant;
import de.dynamicflash.helper.HttpHelper;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 11/11/13
 * Time: 20:16
 */
public class PageListLoader extends AsyncTaskLoader<List<Page>> {

    private List<Page> pages;
    private String path;

    /**
     * @param applicationContext Context
     * @param path               json list url
     */
    public PageListLoader(Context applicationContext, String path) {
        super(applicationContext);
        this.path = path;
    }

    @Override
    protected void onStartLoading() {
        // just make sure if we already have content to deliver
        if (pages != null)
            deliverResult(pages);

        // otherwise if something has been changed or first try
        if (takeContentChanged() || pages == null)
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        pages = null;
    }

    @Override
    public List<Page> loadInBackground() {

        List<Page> result = new ArrayList<Page>();
        JSONArray json;
        try {
            json = new JSONArray(HttpHelper.getString(AppConstant.BASE_URL + path));
            int len = json.length();
            for (int i = 0; i < len; i++) {
                JSONObject jsonObject;
                jsonObject = json.getJSONObject(i);
                result.add(new Page(jsonObject));
            }
        } catch (JSONException e) {
            Log.e(GalleryApplication.TAG, e.getMessage());
        }

        return Collections.unmodifiableList(result);
    }
}
