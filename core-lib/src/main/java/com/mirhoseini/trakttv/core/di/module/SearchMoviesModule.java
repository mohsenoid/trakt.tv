package com.mirhoseini.trakttv.core.di.module;

import com.mirhoseini.trakttv.core.di.scope.SearchMoviesScope;
import com.mirhoseini.trakttv.core.viewmodel.SearchMoviesViewModel;
import com.mirhoseini.trakttv.core.viewmodel.SearchMoviesViewModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 19/07/16.
 */

@Module
public class SearchMoviesModule {

    @Provides
    @SearchMoviesScope
    public SearchMoviesViewModel providePresenter(SearchMoviesViewModelImpl presenter) {
        return presenter;
    }

}
