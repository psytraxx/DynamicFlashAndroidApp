package de.dynamicflash.helper;

import de.dynamicflash.model.Page;
import de.dynamicflash.model.Photo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "https://www.dynamicflash.de";

    @GET("/api/galleries")
    Call<Page[]> getGalleries(@Query("currentpage") Integer pageNumber, @Query("maxresults") Integer perPage);

    @GET("/api/album/{folder}")
    Call<Photo[]> getAlbum(@Path("folder") String folder);

    @GET("/api/projects")
    Call<Page[]> getProjects();
}
