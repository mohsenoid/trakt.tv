package com.mirhoseini.trakttv.core.Presentation;

import com.mirhoseini.trakttv.core.model.SearchMoviesInteractor;
import com.mirhoseini.trakttv.core.view.SearchMoviesView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Mohsen on 19/07/16.
 */

public class SearchMoviesPresenterImpl implements SearchMoviesPresenter {

    private SearchMoviesView view;

    private Subscription subscription = Subscriptions.empty();

    @Inject
    SearchMoviesInteractor interactor;

    @Inject
    public SearchMoviesPresenterImpl() {
    }

    @Override
    public void setView(SearchMoviesView view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void searchMovies(boolean isConnected, String query, int page, int limit) {

        if (null != view) {
            view.showProgress();
        }

        // stop previous search
        if (null != subscription && !subscription.isUnsubscribed())
            subscription.unsubscribe();

        subscription = interactor.searchMovies(query, page, limit).subscribe(movies ->
                {
                    if (null != view) {
                        view.hideProgress();
                        view.setSearchMoviesValue(movies);

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
