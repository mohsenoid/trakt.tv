package com.mirhoseini.trakttv.core.model;

import rx.Observable;
import tv.trakt.api.model.Movie;

/**
 * Created by Mohsen on 19/07/16.
 */

public interface SearchMoviesInteractor {

    Observable<Movie[]> searchMovies(String query, int page, int limit);

    void onDestroy();
}
