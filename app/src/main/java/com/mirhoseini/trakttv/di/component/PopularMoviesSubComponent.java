package com.mirhoseini.trakttv.di.component;

import com.mirhoseini.trakttv.core.di.module.PopularMoviesModule;
import com.mirhoseini.trakttv.core.di.scope.PopularMoviesScope;
import com.mirhoseini.trakttv.view.fragment.PopularMoviesFragment;

import dagger.Subcomponent;

/**
 * Created by Mohsen on 19/07/16.
 */

@PopularMoviesScope
@Subcomponent(modules = {
        PopularMoviesModule.class
})
public interface PopularMoviesSubComponent {

    void inject(PopularMoviesFragment fragment);

}
