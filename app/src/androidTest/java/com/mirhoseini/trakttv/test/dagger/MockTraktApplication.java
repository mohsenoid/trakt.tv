package com.mirhoseini.trakttv.test.dagger;

import com.mirhoseini.trakttv.TraktApplicationImpl;
import com.mirhoseini.trakttv.di.module.AndroidModule;
import com.mirhoseini.trakttv.test.dagger.di.DaggerTestApplicationComponent;
import com.mirhoseini.trakttv.test.dagger.di.MockApiModule;
import com.mirhoseini.trakttv.test.dagger.di.TestApplicationComponent;

public class MockTraktApplication extends TraktApplicationImpl {

    @Override
    public TestApplicationComponent createComponent() {
        return DaggerTestApplicationComponent
                .builder()
                .androidModule(new AndroidModule(this))
                .apiModule(new MockApiModule())
                .build();
    }

}
