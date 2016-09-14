package com.mirhoseini.trakttv.core.test.mockito.model;

import com.mirhoseini.trakttv.core.model.SearchMoviesInteractor;
import com.mirhoseini.trakttv.core.model.SearchMoviesInteractorImpl;
import com.mirhoseini.trakttv.core.client.TraktApi;
import com.mirhoseini.trakttv.core.util.Constants;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import tv.trakt.api.model.Movie;
import tv.trakt.api.model.SearchMovieResult;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Mohsen on 20/07/16.
 */

public class SearchMoviesInteractorImplTest {

    SearchMoviesInteractor interactor;
    TraktApi api;
    SchedulerProvider scheduler;
    SearchMovieResult[] expectedResult;

    @Before
    public void setup() {
        api = mock(TraktApi.class);
        scheduler = mock(SchedulerProvider.class);

        Movie movie = new Movie();
        movie.setTitle("Test Movie");

        SearchMovieResult searchMovieResult = new SearchMovieResult();
        searchMovieResult.setMovie(movie);

        expectedResult = new SearchMovieResult[1];
        expectedResult[0] = searchMovieResult;

        when(scheduler.mainThread())
                .thenReturn(Schedulers.immediate());
        when(scheduler.backgroundThread())
                .thenReturn(Schedulers.immediate());

        when(api.searchMovies(any(String.class), any(Integer.class), any(Integer.class), any(String.class)))
                .thenReturn(Observable.just(expectedResult));

        interactor = new SearchMoviesInteractorImpl(api, scheduler);
    }

    @Test
    public void testSearchMovies() throws Exception {
        TestSubscriber<SearchMovieResult[]> testSubscriber = new TestSubscriber<>();
        interactor.searchMovies("Test Query", 1, 10).subscribe(testSubscriber);

        // search is waiting for more characters
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Collections.emptyList());

        // wait for search to start after a delay (in millis)
        Thread.sleep((Constants.DELAY_BEFORE_SEARCH_STARTED + 1) * 1000);

        // search is done
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(expectedResult));
    }

}
