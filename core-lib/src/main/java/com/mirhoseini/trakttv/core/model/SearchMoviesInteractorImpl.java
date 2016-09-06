package com.mirhoseini.trakttv.core.model;


import com.mirhoseini.trakttv.core.di.scope.SearchMoviesScope;
import com.mirhoseini.trakttv.core.service.TraktApi;
import com.mirhoseini.trakttv.core.util.Constants;
import com.mirhoseini.trakttv.core.util.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subjects.ReplaySubject;
import tv.trakt.api.model.SearchMovieResult;

/**
 * Created by Mohsen on 19/07/16.
 */

@SearchMoviesScope
public class SearchMoviesInteractorImpl implements SearchMoviesInteractor {
    private TraktApi api;
    private SchedulerProvider scheduler;

    private ReplaySubject<SearchMovieResult[]> moviesDataSubject;
    private Subscription moviesSubscription;

    @Inject
    public SearchMoviesInteractorImpl(TraktApi api, SchedulerProvider scheduler) {
        this.api = api;
        this.scheduler = scheduler;
    }

    @Override
    public Observable<SearchMovieResult[]> searchMovies(String query, int page, int limit) {
        if (moviesSubscription != null && !moviesSubscription.isUnsubscribed())
            moviesSubscription.unsubscribe();

        moviesDataSubject = ReplaySubject.create();

        moviesSubscription = api.searchMovies(query, page, limit, Constants.API_EXTENDED_FULL_IMAGES)
                .delaySubscription(Constants.DELAY_BEFORE_SEARCH_STARTED, TimeUnit.SECONDS)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread())
                .subscribe(moviesDataSubject);

        return moviesDataSubject.asObservable();
    }

    @Override
    public void onDestroy() {
        if (moviesSubscription != null && !moviesSubscription.isUnsubscribed())
            moviesSubscription.unsubscribe();
    }

}
