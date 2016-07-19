package com.mirhoseini.trakttv.core.Presentation;

import com.mirhoseini.trakttv.core.model.PopularMoviesInteractor;
import com.mirhoseini.trakttv.core.view.PopularMoviesView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Mohsen on 19/07/16.
 */

public class PopularMoviesPresenterImpl implements PopularMoviesPresenter {

    private PopularMoviesView view;

    private Subscription subscription = Subscriptions.empty();

    @Inject
    PopularMoviesInteractor interactor;

    @Inject
    public PopularMoviesPresenterImpl() {
    }

    @Override
    public void setView(PopularMoviesView view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();

        interactor.onDestroy();

        view = null;
        interactor = null;
    }

    @Override
    public void loadPopularMoviesData(boolean isConnected, int page, int limit) {

        if (null != view) {
            view.showProgress();
        }

        subscription = interactor.loadPopularMovies(page, limit).subscribe(movies ->
                {
                    if (null != view) {
                        view.hideProgress();
                        view.setPopularMoviesValue(movies);

                        if (!isConnected)
                            view.showOfflineMessage();
                    }
                },
                throwable -> {
                    if (null != view) {
                        view.hideProgress();
                    }

                    if (isConnected) {
                        if (null != view) {
                            view.showRetryMessage();
                        }
                    } else {
                        if (null != view) {
                            view.showOfflineMessage();
                        }
                    }
                });

    }
}
