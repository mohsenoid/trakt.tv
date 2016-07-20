package com.mirhoseini.trakttv.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.core.Presentation.PopularMoviesPresenter;
import com.mirhoseini.trakttv.core.di.module.PopularMoviesModule;
import com.mirhoseini.trakttv.core.view.BaseView;
import com.mirhoseini.trakttv.core.view.PopularMoviesView;
import com.mirhoseini.trakttv.di.component.ApplicationComponent;
import com.mirhoseini.trakttv.util.EndlessRecyclerViewScrollListener;
import com.mirhoseini.trakttv.view.adapter.PopularMoviesRecyclerViewAdapter;
import com.mirhoseini.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import tv.trakt.api.model.Movie;

/**
 * Created by Mohsen on 19/07/16.
 */

public class PopularMoviesFragment extends BaseFragment implements PopularMoviesView, SwipeRefreshLayout.OnRefreshListener {

    static final int LIMIT = 10;

    @Inject
    Context context;
    @Inject
    PopularMoviesPresenter presenter;

    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.no_internet)
    ImageView noInternet;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.progress_more)
    ProgressBar progressMore;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    int page;

    private OnListFragmentInteractionListener listener;
    private PopularMoviesRecyclerViewAdapter adapter;

    public PopularMoviesFragment() {
    }

    public static PopularMoviesFragment newInstance() {
        PopularMoviesFragment fragment = new PopularMoviesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        ButterKnife.bind(this, view);

        swipeRefresh.setOnRefreshListener(this);

        if (adapter == null)
            loadPopularMoviesData();
        else
            initRecyclerView();

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
                .plus(new PopularMoviesModule(this))
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
            swipeRefresh.setRefreshing(true);
        } else {
            progressMore.setVisibility(View.VISIBLE);
        }

        noInternet.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
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

        noInternet.setVisibility(View.VISIBLE);
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
                .setAction(R.string.load_retry, v -> loadPopularMoviesData())
                .setActionTextColor(Color.RED)
                .show();
    }

    private void loadPopularMoviesData() {
        page = 1;
        adapter = null;
        presenter.loadPopularMoviesData(Utils.isConnected(context), page, LIMIT);
    }

    private void loadMorePopularMoviesData(int newPage) {
        page = newPage;
        presenter.loadPopularMoviesData(Utils.isConnected(context), page, LIMIT);
    }

    @Override
    public void setPopularMoviesValue(Movie[] movies) {
        Timber.d("Loaded Page: %d", page);

        if (null == adapter) {
            adapter = new PopularMoviesRecyclerViewAdapter(movies, listener);
            initRecyclerView();
        } else {
            adapter.addMoreMovies(movies);
            adapter.notifyDataSetChanged();
        }

        page++;
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Timber.d("Loading more movies, Page: %d", page + 1);

                loadMorePopularMoviesData(page + 1);
            }
        });
    }

    @Override
    public void onRefresh() {
        loadPopularMoviesData();
    }

    public interface OnListFragmentInteractionListener extends BaseView {

        void onListFragmentInteraction(Movie movie);

    }
}
