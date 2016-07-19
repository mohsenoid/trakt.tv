package com.mirhoseini.trakttv.core.di.module;

import com.mirhoseini.trakttv.core.Presentation.SearchMoviesPresenter;
import com.mirhoseini.trakttv.core.Presentation.SearchMoviesPresenterImpl;
import com.mirhoseini.trakttv.core.view.SearchMoviesView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 19/07/16.
 */

@Module
public class SearchMoviesModule {
    private SearchMoviesView view;

    public SearchMoviesModule(SearchMoviesView view) {
        this.view = view;
    }

    @Provides
    public SearchMoviesView provideView() {
        return view;
    }

    @Provides
    public SearchMoviesPresenter providePresenter(SearchMoviesPresenterImpl presenter) {
        presenter.setView(view);
        return presenter;
    }
}
