package de.dynamicflash.helper;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 09/11/13
 * Time: 18:24
 */

public class AppConstant {


    public static final String BASE_URL = "https://www.dynamicflash.de";

    public static final String GALLERY_LIST_JSON =  "/json/galleries?currentpage=%s&maxresults=%s";

    public static final String PROJECT_LIST_JSON =  "/json/projects";

    /**
     * http://de.dynamicflash.de/json/album/BangkokBaliLombok
     */
    public static final String ALBUM_LIST_JSON =  "/json/album/%s";

    /**
     *     http://de.dynamicflash.de/media/cache/thumb_145/BangkokBaliLombok/IMG_0863.jpg
     */
    public static final String THUMB_IMAGE =  "/media/cache/resolve/thumb_photo_320/%s";

    /**
     *     http://de.dynamicflash.de/media/cache/full_1024/BangkokBaliLombok/IMG_0694.jpg
     */
    public static final String FULL_IMAGE =  "/media/cache/resolve/thumb_photo_1280/%s";

}