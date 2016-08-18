package com.mirhoseini.trakttv.core.viewmodel;

import java.util.ArrayList;

import rx.Observable;
import tv.trakt.api.model.Movie;
import tv.trakt.api.model.SearchMovieResult;

/**
 * Created by Mohsen on 19/07/16.
 */

public interface SearchMoviesViewModel extends BaseViewModel {

    Observable<ArrayList<SearchMovieResult>> searchMoviesObservable(String query, int page, int limit);

     Observable<ArrayList<SearchMovieResult>> moviesObservable();

}
