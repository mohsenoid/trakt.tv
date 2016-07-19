package com.mirhoseini.trakttv.core.view;

import tv.trakt.api.model.Movie;

/**
 * Created by Mohsen on 19/07/16.
 */

public interface SearchMoviesView extends BaseView {

    void showProgress();

    void hideProgress();

    void setSearchedMoviesValue(Movie[] movies);

    void showRetryMessage();

}
