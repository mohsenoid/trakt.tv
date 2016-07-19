package com.mirhoseini.trakttv.core.Presentation;

import com.mirhoseini.trakttv.core.model.SearchMoviesInteractor;
import com.mirhoseini.trakttv.core.view.SearchMoviesView;

import javax.inject.Inject;

/**
 * Created by Mohsen on 19/07/16.
 */

public class SearchMoviesPresenterImpl implements SearchMoviesPresenter {

    private SearchMoviesView view;

    @Inject
    SearchMoviesInteractor interactor;

    @Inject
    public SearchMoviesPresenterImpl() {
    }

    @Override
    public void setView(SearchMoviesView view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
    }

}
