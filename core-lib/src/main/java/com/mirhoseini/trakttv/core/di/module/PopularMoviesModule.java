package com.mirhoseini.trakttv.core.di.module;

import com.mirhoseini.trakttv.core.di.scope.PopularMoviesScope;
import com.mirhoseini.trakttv.core.viewmodel.PopularMoviesViewModel;
import com.mirhoseini.trakttv.core.viewmodel.PopularMoviesViewModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 19/07/16.
 */

@Module
public class PopularMoviesModule {

    @Provides
    @PopularMoviesScope
    public PopularMoviesViewModel providePresenter(PopularMoviesViewModelImpl viewModel) {
        return viewModel;
    }

}
