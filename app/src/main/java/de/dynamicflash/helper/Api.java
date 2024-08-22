package de.dynamicflash.helper;

import java.util.List;

import de.dynamicflash.model.PageResult;
import de.dynamicflash.model.Photo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    String BASE_URL = "https://www.dynamicflash.de";

    @GET("/api/photosbyalbumname/{folder}")
    Call<List<Photo>> getPhotosByAlbumName(@Path("folder") String folder);

    @GET("/api/childpages/{pageid}/{page}")
    Call<PageResult> getChildPages(@Path("pageid") String pageid, @Path("page") Integer page);
}
