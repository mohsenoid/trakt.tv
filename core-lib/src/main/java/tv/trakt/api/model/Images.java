package tv.trakt.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mohsen on 19/07/16.
 */

public class Images implements Serializable {

    @SerializedName("fanart")
    @Expose
    private FanArt fanArt;
    @SerializedName("poster")
    @Expose
    private Poster poster;
    @SerializedName("logo")
    @Expose
    private Logo logo;
    @SerializedName("clearart")
    @Expose
    private ClearArt clearArt;
    @SerializedName("banner")
    @Expose
    private Banner banner;
    @SerializedName("thumb")
    @Expose
    private Thumb thumb;

    public FanArt getFanArt() {
        return fanArt;
    }

    public void setFanArt(FanArt fanArt) {
        this.fanArt = fanArt;
    }

    public Poster getPoster() {
        return poster;
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public ClearArt getClearArt() {
        return clearArt;
    }

    public void setClearArt(ClearArt clearArt) {
        this.clearArt = clearArt;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

}
