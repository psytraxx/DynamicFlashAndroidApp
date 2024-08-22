package de.dynamicflash;

import android.app.Application;

import java.util.List;

import de.dynamicflash.model.Photo;

public class GalleryApplication extends Application {

    /**
     * to avoid loading the same data for the swipe view (is loaded with thumbs for the overview view)
     */
    public static final String TAG = GalleryApplication.class.getName();
    private List<Photo> currentPhotos;

    public List<Photo> getCurrentPhotos() {
        return currentPhotos;
    }

    public void setCurrentPhotos(List<Photo> currentPhotos) {
        this.currentPhotos = currentPhotos;
    }
}

