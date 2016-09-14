package com.mirhoseini.trakttv.core.viewmodel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import tv.trakt.api.model.Movie;

/**
 * Created by Mohsen on 19/07/16.
 */

public interface PopularMoviesViewModel extends BaseViewModel{

    Observable<ArrayList<Movie>> loadPopularMoviesDataObservable(int page, int limit);

    Observable<ArrayList<Movie>> moviesObservable();

}
