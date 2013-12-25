package de.dynamicflash.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:09
 */
public class Page {

    private String folder;
    private String title;
    private String body;
    private String image;
    private String url;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    private Date created;

    public Page(JSONObject jsonObject) throws JSONException {
        setTitle(jsonObject.getString("title"));
        setBody(jsonObject.getString("body"));
        if (jsonObject.has("title"))
            setFolder(jsonObject.getString("folder"));
        if (jsonObject.has("image"))
            setImage(jsonObject.getString("image"));
        if (jsonObject.has("url"))
            setUrl(jsonObject.getString("url"));
        if (jsonObject.has("created"))
            setCreated(new Date(jsonObject.getLong("created")));
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
