package com.mirhoseini.trakttv.di.component;

import com.mirhoseini.trakttv.core.di.module.ApiModule;
import com.mirhoseini.trakttv.core.di.module.ClientModule;
import com.mirhoseini.trakttv.core.di.module.PopularMoviesModule;
import com.mirhoseini.trakttv.core.di.module.SearchMoviesModule;
import com.mirhoseini.trakttv.di.module.AndroidModule;
import com.mirhoseini.trakttv.di.module.ApplicationModule;
import com.mirhoseini.trakttv.view.activity.DetailsActivity;
import com.mirhoseini.trakttv.view.activity.MainActivity;
import com.mirhoseini.trakttv.view.activity.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;
import com.mirhoseini.trakttv.view.fragment.DetailsFragment;

/**
 * Created by Mohsen on 19/07/16.
 */

@Singleton
@Component(modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        ClientModule.class
})
public interface ApplicationComponent {

    PopularMoviesSubComponent plus(PopularMoviesModule module);

    SearchMoviesSubComponent plus(SearchMoviesModule module);

    void inject(SplashActivity activity);

    void inject(MainActivity activity);

    void inject(DetailsActivity activity);

    void inject(DetailsFragment fragment);
}