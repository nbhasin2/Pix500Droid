package com.model.json;

/**
 * Created by nishant on 16-03-15.
 */
public class PhotoData {
    public String thumbnailUrl = "";
    public String highResolutionUrl = "";

    public PhotoData(String thumbnailUrl, String highResolutionUrl)
    {
        this.thumbnailUrl = thumbnailUrl;
        this.highResolutionUrl = thumbnailUrl;
    }
}
