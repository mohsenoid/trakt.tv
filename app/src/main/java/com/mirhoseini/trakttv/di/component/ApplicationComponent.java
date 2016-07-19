package com.mirhoseini.trakttv.di.component;

import com.mirhoseini.trakttv.core.di.module.ApiModule;
import com.mirhoseini.trakttv.core.di.module.ClientModule;
import com.mirhoseini.trakttv.core.di.module.PopularMoviesModule;
import com.mirhoseini.trakttv.core.di.module.SearchMoviesModule;
import com.mirhoseini.trakttv.di.module.AndroidModule;
import com.mirhoseini.trakttv.di.module.ApplicationModule;
import com.mirhoseini.trakttv.view.activity.MainActivity;
import com.mirhoseini.trakttv.view.activity.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

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

    PopularMoviesComponent plus(PopularMoviesModule module);

    SearchMoviesComponent plus(SearchMoviesModule module);

    void inject(SplashActivity activity);

    void inject(MainActivity activity);

}