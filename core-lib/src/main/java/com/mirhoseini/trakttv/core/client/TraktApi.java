package com.mirhoseini.trakttv.core.client;


import com.mirhoseini.trakttv.core.util.Constants;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;
import tv.trakt.api.model.Movie;
import tv.trakt.api.model.SearchMovieResult;

/**
 * Created by Mohsen on 19/07/16.
 */

public interface TraktApi {

    // https://api.trakt.tv/movies/popular?page=1&limit=10&extended=full,images
    @GET("movies/popular")
    @Headers({
            "Content-Type:" + Constants.API_CONTENT_TYPE_JSON,
            "trakt-api-version:" + Constants.API_VERSION_2,
            "trakt-api-key:" + Constants.API_KEY,
    })
    Observable<Movie[]> getPopularMovies(@Query("page") int page, @Query("limit") int limit, @Query("extended") String extended);

    // https://api.trakt.tv/search/movie?query=tron&page=1&limit=10&extended=full,images
    @GET("search/movie")
    @Headers({
            "Content-Type:" + Constants.API_CONTENT_TYPE_JSON,
            "trakt-api-version:" + Constants.API_VERSION_2,
            "trakt-api-key:" + Constants.API_KEY,
    })
    Observable<SearchMovieResult[]> searchMovies(@Query("query") String query, @Query("page") int page, @Query("limit") int limit, @Query("extended") String extended);

}
