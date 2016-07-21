package com.mirhoseini.trakttv.test.dagger.di;

import com.mirhoseini.trakttv.core.di.module.ApiModule;
import com.mirhoseini.trakttv.core.di.module.ClientModule;
import com.mirhoseini.trakttv.di.component.ApplicationComponent;
import com.mirhoseini.trakttv.di.module.AndroidModule;
import com.mirhoseini.trakttv.di.module.ApplicationModule;
import com.mirhoseini.trakttv.test.dagger.MainActivityDaggerTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        ClientModule.class
})
public interface TestApplicationComponent extends ApplicationComponent {

    void inject(MainActivityDaggerTest test);

}