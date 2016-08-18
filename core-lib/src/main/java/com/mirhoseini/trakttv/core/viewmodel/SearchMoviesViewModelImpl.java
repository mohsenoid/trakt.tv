package com.mirhoseini.trakttv.core.viewmodel;

import com.mirhoseini.trakttv.core.client.TraktApi;
import com.mirhoseini.trakttv.core.util.Constants;
import com.mirhoseini.trakttv.core.util.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subjects.BehaviorSubject;
import rx.subjects.ReplaySubject;
import tv.trakt.api.model.SearchMovieResult;

/**
 * Created by Mohsen on 19/07/16.
 */

public class SearchMoviesViewModelImpl implements SearchMoviesViewModel {

    private TraktApi api;
    private SchedulerProvider scheduler;

    private ReplaySubject<SearchMovieResult[]> moviesDataSubject;
    private Subscription moviesSubscription;

    private BehaviorSubject<Boolean> isLoadingSubject = BehaviorSubject.create(false);


    @Inject
    public SearchMoviesViewModelImpl(TraktApi api, SchedulerProvider scheduler) {
        this.api = api;
        this.scheduler = scheduler;
    }

//    @Override
//    public void destroy() {
//        view = null;
//    }

    @Override
    public Observable<SearchMovieResult[]> searchMoviesObservable(String query, int page, int limit) {

        // Don't try and load if we're already loading or query is empty
        if (isLoadingSubject.getValue() || query.isEmpty()) {
            return Observable.empty();
        }

        //show loading progress
        isLoadingSubject.onNext(true);

        // stop previous search
        if (null != moviesSubscription && !moviesSubscription.isUnsubscribed())
            moviesSubscription.unsubscribe();

        if (moviesSubscription != null && !moviesSubscription.isUnsubscribed())
            moviesSubscription.unsubscribe();

        moviesDataSubject = ReplaySubject.create();

        moviesSubscription = api.searchMovies(query, page, limit, Constants.API_EXTENDED_FULL_IMAGES)
                .delaySubscription(Constants.DELAY_BEFORE_SEARCH_STARTED, TimeUnit.SECONDS)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread())
                .doOnTerminate(() -> isLoadingSubject.onNext(false))
                .subscribe(moviesDataSubject);

        return moviesDataSubject.asObservable();


//            subscription = interactor.searchMovies(query, page, limit).subscribe(searchResult ->
//                    {
//                        if (null != view) {
//                            view.hideProgress();
//                            view.setSearchMoviesValue(searchResult);
//
//                            if (!isConnected)
//                                view.showOfflineMessage();
//                        }
//                    },
//                    throwable -> {
//                        if (null != view) {
//                            view.hideProgress();
//                        }
//
//                        if (isConnected) {
//                            if (null != view) {
//                                view.showRetryMessage();
//                            }
//                        } else {
//                            if (null != view) {
//                                view.showOfflineMessage();
//                            }
//                        }
//                    });

    }

    public Observable<Boolean> isLoadingObservable() {
        return isLoadingSubject.asObservable();
    }
}
