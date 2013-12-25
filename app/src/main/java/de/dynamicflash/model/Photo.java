package de.dynamicflash.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 12:16
 */
public class Photo {
    private String full_path;
    private String title;

    public Photo(JSONObject jsonObject) throws JSONException {
        setFull_path(jsonObject.getString("full_path"));
        setTitle(jsonObject.getString("title"));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFull_path() {
        return full_path;
    }

    public void setFull_path(String full_path) {
        this.full_path = full_path;
    }


}
