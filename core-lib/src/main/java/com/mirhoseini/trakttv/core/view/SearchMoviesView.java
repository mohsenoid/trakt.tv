package com.mirhoseini.trakttv.core.view;

import tv.trakt.api.model.SearchMovieResult;

/**
 * Created by Mohsen on 19/07/16.
 */

public interface SearchMoviesView extends BaseView {

    void showProgress();

    void hideProgress();

    void setSearchMoviesValue(SearchMovieResult[] searchMovieResults);

    void showRetryMessage();

}
