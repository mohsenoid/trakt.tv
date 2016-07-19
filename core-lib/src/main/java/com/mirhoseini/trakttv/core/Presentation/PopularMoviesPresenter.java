package com.mirhoseini.trakttv.core.Presentation;

import com.mirhoseini.trakttv.core.view.PopularMoviesView;

/**
 * Created by Mohsen on 19/07/16.
 */

public interface PopularMoviesPresenter extends BasePresenter<PopularMoviesView> {

    void loadPopularMoviesData(boolean isConnected, int page, int limit);

}
