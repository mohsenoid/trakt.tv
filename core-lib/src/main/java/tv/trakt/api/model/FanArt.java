package tv.trakt.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mohsen on 19/07/16.
 */

public class FanArt implements Serializable {

    @SerializedName("full")
    @Expose
    private String full;
    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("thumb")
    @Expose
    private String thumb;

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

}
