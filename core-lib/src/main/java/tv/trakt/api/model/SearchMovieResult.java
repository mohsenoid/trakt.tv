package tv.trakt.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohsen on 19/07/16.
 */
public class SearchMovieResult {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("score")
    @Expose
    private Double score;
    @SerializedName("movie")
    @Expose
    private Movie movie;

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The score
     */
    public Double getScore() {
        return score;
    }

    /**
     * @param score The score
     */
    public void setScore(Double score) {
        this.score = score;
    }

    /**
     * @return The movie
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * @param movie The movie
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
