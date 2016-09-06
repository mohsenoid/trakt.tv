package com.mirhoseini.trakttv.core.Presentation;

import com.mirhoseini.trakttv.core.model.SearchMoviesInteractor;
import com.mirhoseini.trakttv.core.view.SearchMoviesView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.Subscriptions;
import tv.trakt.api.model.SearchMovieResult;

/**
 * Created by Mohsen on 19/07/16.
 */

public class SearchMoviesPresenterImpl implements SearchMoviesPresenter {

    @Inject
    SearchMoviesInteractor interactor;
    private SearchMoviesView view;
    private Subscription subscription = Subscriptions.empty();

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

        if (null == query || query.isEmpty()) {
            if (null != view) {
                view.hideProgress();
            }

            view.setSearchMoviesValue(new SearchMovieResult[0]);
        } else {
            subscription = interactor.searchMovies(query, page, limit).subscribe(searchResult ->
                    {
                        if (null != view) {
                            view.hideProgress();
                            view.setSearchMoviesValue(searchResult);

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
}
