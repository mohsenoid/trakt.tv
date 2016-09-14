package com.mirhoseini.trakttv.core.test.mockito.model;

import com.mirhoseini.trakttv.core.model.PopularMoviesInteractor;
import com.mirhoseini.trakttv.core.model.PopularMoviesInteractorImpl;
import com.mirhoseini.trakttv.core.client.TraktApi;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import tv.trakt.api.model.Movie;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Mohsen on 20/07/16.
 */

public class PopularMoviesInteractorImplTest {

    PopularMoviesInteractor interactor;
    TraktApi api;
    SchedulerProvider scheduler;
    Movie[] expectedResult;

    @Before
    public void setup() {
        api = mock(TraktApi.class);
        scheduler = mock(SchedulerProvider.class);

        Movie movie = new Movie();
        movie.setTitle("Test Movie");

        expectedResult = new Movie[1];
        expectedResult[0] = movie;

        when(scheduler.mainThread())
                .thenReturn(Schedulers.immediate());
        when(scheduler.backgroundThread())
                .thenReturn(Schedulers.immediate());

        when(api.getPopularMovies(any(Integer.class), any(Integer.class), any(String.class)))
                .thenReturn(Observable.just(expectedResult));

        interactor = new PopularMoviesInteractorImpl(api, scheduler);
    }

    @Test
    public void testLoadPopularMovies() throws Exception {
        TestSubscriber<Movie[]> testSubscriber = new TestSubscriber<>();
        interactor.loadPopularMovies(1, 10).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(expectedResult));
    }

}
