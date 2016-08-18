package com.mirhoseini.trakttv.core.viewmodel;

import com.mirhoseini.trakttv.core.client.TraktApi;
import com.mirhoseini.trakttv.core.util.Constants;
import com.mirhoseini.trakttv.core.util.SchedulerProvider;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subjects.BehaviorSubject;
import rx.subjects.ReplaySubject;
import tv.trakt.api.model.Movie;

/**
 * Created by Mohsen on 19/07/16.
 */

public class PopularMoviesViewModelImpl implements PopularMoviesViewModel {

    private TraktApi api;
    private SchedulerProvider scheduler;

    private ReplaySubject<Movie[]> moviesDataSubject;
    private Subscription moviesSubscription;

    private BehaviorSubject<Boolean> isLoadingSubject = BehaviorSubject.create(false);

    @Inject
    public PopularMoviesViewModelImpl(TraktApi api, SchedulerProvider scheduler) {
        this.api = api;
        this.scheduler = scheduler;
    }

//    @Override
//    public void destroy() {
//        if (subscription != null && !subscription.isUnsubscribed())
//            subscription.unsubscribe();
//
//        interactor.onDestroy();
//
//        view = null;
//        interactor = null;
//    }

    @Override
    public Observable<Movie[]> loadPopularMoviesDataObservable(int page, int limit) {

        // Don't try and load if we're already loading
        if (isLoadingSubject.getValue()) {
            return Observable.empty();
        }

        //show loading progress
        isLoadingSubject.onNext(true);

        if (moviesSubscription == null || moviesSubscription.isUnsubscribed()) {
            moviesDataSubject = ReplaySubject.create();

            moviesSubscription = api.getPopularMovies(page, limit, Constants.API_EXTENDED_FULL_IMAGES)
                    .subscribeOn(scheduler.backgroundThread())
                    .observeOn(scheduler.mainThread())
                    .doOnTerminate(() -> isLoadingSubject.onNext(false))
                    .subscribe(moviesDataSubject);
        }

        return moviesDataSubject.asObservable();

//        subscription = interactor.loadPopularMovies(page, limit).subscribe(movies ->
//                {
//                    if (null != view) {
//                        view.hideProgress();
//                        view.setPopularMoviesValue(movies);
//
//                        if (!isConnected)
//                            view.showOfflineMessage();
//                    }
//                },
//                throwable -> {
//                    if (null != view) {
//                        view.hideProgress();
//                    }
//
//                    if (isConnected) {
//                        if (null != view) {
//                            view.showRetryMessage();
//                        }
//                    } else {
//                        if (null != view) {
//                            view.showOfflineMessage();
//                        }
//                    }
//                });

    }

    public Observable<Boolean> isLoadingObservable() {
        return isLoadingSubject.asObservable();
    }
}
