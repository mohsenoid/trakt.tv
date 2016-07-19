package com.mirhoseini.trakttv.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.mirhoseini.trakttv.TraktApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 19/07/16.
 */

@Module
public class AndroidModule {
    private TraktApplication traktApplication;

    public AndroidModule(TraktApplication traktApplication) {
        this.traktApplication = traktApplication;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return traktApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    public Resources provideResources() {
        return traktApplication.getResources();
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(traktApplication);
    }

}
