package com.mirhoseini.trakttv.core.viewmodel;

import com.mirhoseini.trakttv.core.client.TraktApi;
import com.mirhoseini.trakttv.core.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import tv.trakt.api.model.Movie;

/**
 * Created by Mohsen on 19/07/16.
 */

public class PopularMoviesViewModelImpl implements PopularMoviesViewModel {

    private TraktApi api;

    private BehaviorSubject<ArrayList<Movie>> subject = BehaviorSubject.create();
    private BehaviorSubject<Boolean> isLoadingSubject = BehaviorSubject.create(false);

    @Inject
    public PopularMoviesViewModelImpl(TraktApi api) {
        this.api = api;
    }

    @Override
    public Observable<ArrayList<Movie>> loadPopularMoviesDataObservable(int page, int limit) {

        // Don't try and load if we're already loading
        if (isLoadingSubject.getValue()) {
            return Observable.empty();
        }

        //show loading progress
        isLoadingSubject.onNext(true);

        return api.getPopularMovies(page, limit, Constants.API_EXTENDED_FULL_IMAGES)
                //convert Movies array to List
                .map(movies -> new ArrayList<>(Arrays.asList(movies)))
                // Concatenate the new movies to the current posts list, then emit it via the subject
                .doOnNext(moviesList -> {
                    ArrayList fullList;

                    if (page == 1)
                        fullList = new ArrayList();
                    else
                        fullList = new ArrayList(subject.getValue());

                    fullList.addAll(moviesList);
                    subject.onNext(fullList);
                })
                .doOnError(throwable -> subject.onError(new Throwable("Network error!")))
                .doOnTerminate(() -> isLoadingSubject.onNext(false));
    }

    @Override
    public Observable<ArrayList<Movie>> moviesObservable() {
        return subject.asObservable();
    }

    @Override
    public Observable<Boolean> isLoadingObservable() {
        return isLoadingSubject.asObservable();
    }
}
