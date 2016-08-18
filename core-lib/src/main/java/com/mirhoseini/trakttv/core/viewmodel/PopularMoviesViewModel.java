package com.mirhoseini.trakttv.core.viewmodel;

import rx.Observable;
import tv.trakt.api.model.Movie;

/**
 * Created by Mohsen on 19/07/16.
 */

public interface PopularMoviesViewModel {

    Observable<Movie[]> loadPopularMoviesDataObservable(int page, int limit);

}
