package com.model.json;

import java.io.Serializable;

/**
 * Created by nishant on 16-03-15.
 */
public class PhotoData implements Serializable {
    public String thumbnailUrl = "";
    public String highResolutionUrl = "";

    public PhotoData(String thumbnailUrl, String highResolutionUrl)
    {
        this.thumbnailUrl = thumbnailUrl;
        this.highResolutionUrl = highResolutionUrl;
    }
}
