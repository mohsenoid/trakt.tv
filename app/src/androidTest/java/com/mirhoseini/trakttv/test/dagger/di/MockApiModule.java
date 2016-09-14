package com.mirhoseini.trakttv.test.dagger.di;

import com.mirhoseini.trakttv.core.di.module.ApiModule;
import com.mirhoseini.trakttv.core.client.TraktApi;

import retrofit2.Retrofit;

import static org.mockito.Mockito.mock;

public class MockApiModule extends ApiModule {

    @Override
    public TraktApi provideTraktApiService(Retrofit retrofit) {
        return mock(TraktApi.class);
    }

}
