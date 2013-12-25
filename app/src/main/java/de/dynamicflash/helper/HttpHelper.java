package de.dynamicflash.helper;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.dynamicflash.GalleryApplication;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 10:18
 */
public class HttpHelper {


    public static String getString(String url) {
        try {
            Log.i(GalleryApplication.TAG, "getJson: " + url);
            URL u = new URL(url);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setAllowUserInteraction(false);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_CREATED:
                    InputStream inputStream = c.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    bufferedReader.close();
                    inputStreamReader.close();
                    inputStream.close();
                    return sb.toString();
                default:
                    Log.e(GalleryApplication.TAG, url + " return a response code of" + status);
            }

        } catch (MalformedURLException ex) {
            Log.e(GalleryApplication.TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.e(GalleryApplication.TAG, ex.getMessage());
        }
        return null;
    }

}
