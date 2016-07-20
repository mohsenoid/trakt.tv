package com.mirhoseini.trakttv.core.model;

import rx.Observable;
import tv.trakt.api.model.SearchMovieResult;

/**
 * Created by Mohsen on 19/07/16.
 */

public interface SearchMoviesInteractor {

    Observable<SearchMovieResult[]> searchMovies(String query, int page, int limit);

    void onDestroy();
}
