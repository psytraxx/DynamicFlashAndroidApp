package de.dynamicflash.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
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
import de.dynamicflash.model.Photo;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 11/11/13
 * Time: 20:16
 */
public class PhotoListLoader extends AsyncTaskLoader<List<Photo>> {

    private List<Photo> photos;
    private String folder;

    public PhotoListLoader(Context applicationContext, String folder) {
        super(applicationContext);
        this.folder = folder;
    }

    @Override
    protected void onStartLoading() {
        // just make sure if we already have content to deliver
        if (photos != null)
            deliverResult(photos);

        // otherwise if something has been changed or first try
        if (takeContentChanged() || photos == null)
            forceLoad();
    }


    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        photos = null;
    }

    @Override
    public List<Photo> loadInBackground() {

        List<Photo> result = new ArrayList<Photo>();
        try {
            JSONArray json = new JSONArray(HttpHelper.getString(AppConstant.BASE_URL + String.format(AppConstant.ALBUM_LIST_JSON, folder)));
            int len = json.length();
            for (int i = 0; i < len; i++) {
                JSONObject jsonObject = json.getJSONObject(i);
                result.add(new Photo(jsonObject));
            }
        } catch (JSONException e) {
            Log.e(GalleryApplication.TAG, e.getMessage());
        }
        return Collections.unmodifiableList(result);
    }
}
