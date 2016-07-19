package com.mirhoseini.trakttv;

import android.app.Application;

import com.mirhoseini.trakttv.di.component.ApplicationComponent;
import com.mirhoseini.trakttv.di.component.DaggerApplicationComponent;
import com.mirhoseini.trakttv.di.module.AndroidModule;

/**
 * Created by Mohsen on 19/07/16.
 */

public abstract class TraktApplication extends Application {
    private static ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return component;
    }

    protected AndroidModule getAndroidModule() {
        return new AndroidModule(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initApplication();

        component = DaggerApplicationComponent.builder()
                .androidModule(getAndroidModule())
                .build();
    }

    abstract void initApplication();
}
