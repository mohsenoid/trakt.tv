package com.mirhoseini.trakttv.core.viewmodel;

import rx.Observable;
import tv.trakt.api.model.SearchMovieResult;

/**
 * Created by Mohsen on 19/07/16.
 */

public interface SearchMoviesViewModel {

    Observable<SearchMovieResult[]> searchMoviesObservable(String query, int page, int limit);

}
