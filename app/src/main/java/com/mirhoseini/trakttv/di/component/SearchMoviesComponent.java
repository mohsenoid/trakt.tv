package com.mirhoseini.trakttv.di.component;

import com.mirhoseini.trakttv.core.di.scope.SearchMoviesScope;
import com.mirhoseini.trakttv.core.di.module.SearchMoviesModule;
import com.mirhoseini.trakttv.view.fragment.SearchMoviesFragment;

import dagger.Subcomponent;

/**
 * Created by Mohsen on 19/07/16.
 */

@SearchMoviesScope
@Subcomponent(modules = {
        SearchMoviesModule.class
})
public interface SearchMoviesComponent {

    void inject(SearchMoviesFragment fragment);

}