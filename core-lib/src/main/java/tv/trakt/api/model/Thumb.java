package tv.trakt.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mohsen on 19/07/16.
 */

public class Thumb implements Serializable {

    @SerializedName("full")
    @Expose
    private String full;

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

}
