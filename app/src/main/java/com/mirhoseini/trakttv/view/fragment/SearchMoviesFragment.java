package com.mirhoseini.trakttv.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.core.Presentation.SearchMoviesPresenter;
import com.mirhoseini.trakttv.core.di.module.SearchMoviesModule;
import com.mirhoseini.trakttv.core.util.Constants;
import com.mirhoseini.trakttv.core.view.BaseView;
import com.mirhoseini.trakttv.core.view.SearchMoviesView;
import com.mirhoseini.trakttv.di.component.ApplicationComponent;
import com.mirhoseini.trakttv.util.EndlessRecyclerViewScrollListener;
import com.mirhoseini.trakttv.util.ItemSpaceDecoration;
import com.mirhoseini.trakttv.view.adapter.SearchMoviesRecyclerViewAdapter;
import com.mirhoseini.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;
import tv.trakt.api.model.Movie;
import tv.trakt.api.model.SearchMovieResult;

/**
 * Created by Mohsen on 19/07/16.
 */

public class SearchMoviesFragment extends BaseFragment implements SearchMoviesView {

    @Inject
    public SearchMoviesPresenter presenter;
    @Inject
    Context context;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.no_internet)
    ImageView noInternet;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.progress_more)
    ProgressBar progressMore;
    @BindView(R.id.no_result_found)
    TextView noResultFound;
    int page;
    String query;
    private OnListFragmentInteractionListener listener;
    private SearchMoviesRecyclerViewAdapter adapter;
    public SearchMoviesFragment() {
    }

    public static SearchMoviesFragment newInstance() {
        SearchMoviesFragment fragment = new SearchMoviesFragment();
        return fragment;
    }

    @OnClick(R.id.no_internet)
    void onNoInternetClick(View view) {
        searchMovies();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        // add material margins to list items card view
        recyclerView.addItemDecoration(new ItemSpaceDecoration(Constants.RECYCLER_VIEW_ITEM_SPACE));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component
                .plus(new SearchMoviesModule(this))
                .inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;

        presenter.destroy();
        presenter = null;
    }

    @Override
    public void showProgress() {
        if (page == 1) {
            progress.setVisibility(View.VISIBLE);
        } else {
            progressMore.setVisibility(View.VISIBLE);
        }

        noResultFound.setVisibility(View.GONE);
        noInternet.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
        progressMore.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        if (null != listener) {
            listener.showMessage(message);
        }
    }

    @Override
    public void showOfflineMessage() {
        if (null != listener) {
            listener.showOfflineMessage();
        }

        if (null == adapter || adapter.getItemCount() == 0) {
            noInternet.setVisibility(View.VISIBLE);
            noResultFound.setVisibility(View.GONE);
        }
    }

    @Override
    public void showConnectionError() {
        if (null != listener) {
            listener.showConnectionError();
        }
    }

    @Override
    public void showRetryMessage() {
        Timber.d("Showing Retry Message");

        Snackbar.make(getView(), R.string.retry_message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.load_retry, v -> searchMovies())
                .setActionTextColor(Color.RED)
                .show();
    }

    private void searchMovies() {
        page = 1;
        adapter = null;
        presenter.searchMovies(Utils.isConnected(context), query, page, Constants.PAGE_ROW_LIMIT);
    }

    private void searchMoreMovies(int newPage) {
        page = newPage;
        presenter.searchMovies(Utils.isConnected(context), query, page, Constants.PAGE_ROW_LIMIT);
    }

    @Override
    public void setSearchMoviesValue(SearchMovieResult[] searchMovieResults) {
        Timber.d("Loaded Page: %d", page);

        if (null == adapter) {
            if (searchMovieResults.length == 0) {
                noResultFound.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                adapter = new SearchMoviesRecyclerViewAdapter(searchMovieResults, listener);
                initRecyclerView();
            }
        } else {
            adapter.addMoreMovies(searchMovieResults);
            adapter.notifyDataSetChanged();
        }

        page++;
    }

    private void initRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Timber.d("Loading more movies, Page: %d", page + 1);

                searchMoreMovies(page + 1);
            }
        });
    }

    public void updateQuery(String query) {
        this.query = query;
        searchMovies();
    }

    public interface OnListFragmentInteractionListener extends BaseView {

        void onListFragmentInteraction(Movie movie);

    }
}
