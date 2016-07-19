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
import android.widget.ProgressBar;

import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.core.Presentation.PopularMoviesPresenter;
import com.mirhoseini.trakttv.core.di.module.PopularMoviesModule;
import com.mirhoseini.trakttv.core.view.BaseView;
import com.mirhoseini.trakttv.core.view.PopularMoviesView;
import com.mirhoseini.trakttv.di.component.ApplicationComponent;
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
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private OnListFragmentInteractionListener listener;

    int page;
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


        loadPopularMoviesData();

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
        if (page == 1)
            progress.setVisibility(View.VISIBLE);

        swipeRefresh.setRefreshing(true);

    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
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

    private void loadMorePopularMoviesData() {
        page++;
        presenter.loadPopularMoviesData(Utils.isConnected(context), page, LIMIT);
    }

    @Override
    public void setPopularMoviesValue(Movie[] movies) {
        page++;

        if (null == adapter) {
            adapter = new PopularMoviesRecyclerViewAdapter(movies, listener);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
        } else {
            adapter.addMoreMovies(movies);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        loadPopularMoviesData();
    }

    public interface OnListFragmentInteractionListener extends BaseView {

        void onListFragmentInteraction(Movie movie);

    }
}