package com.mirhoseini.trakttv.core.di.module;

import com.mirhoseini.trakttv.core.Presentation.PopularMoviesPresenter;
import com.mirhoseini.trakttv.core.Presentation.PopularMoviesPresenterImpl;
import com.mirhoseini.trakttv.core.model.PopularMoviesInteractor;
import com.mirhoseini.trakttv.core.model.PopularMoviesInteractorImpl;
import com.mirhoseini.trakttv.core.view.PopularMoviesView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 19/07/16.
 */

@Module
public class PopularMoviesModule {
    private PopularMoviesView view;

    public PopularMoviesModule(PopularMoviesView view) {
        this.view = view;
    }

    @Provides
    public PopularMoviesView provideView() {
        return view;
    }

    @Provides
    public PopularMoviesInteractor provideInteractor(PopularMoviesInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    public PopularMoviesPresenter providePresenter(PopularMoviesPresenterImpl presenter) {
        presenter.setView(view);
        return presenter;
    }
}
