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
import com.mirhoseini.trakttv.core.viewmodel.PopularMoviesViewModel;
import com.mirhoseini.trakttv.core.di.module.PopularMoviesModule;
import com.mirhoseini.trakttv.core.util.Constants;
import com.mirhoseini.trakttv.di.component.ApplicationComponent;
import com.mirhoseini.trakttv.util.EndlessRecyclerViewScrollListener;
import com.mirhoseini.trakttv.util.ItemSpaceDecoration;
import com.mirhoseini.trakttv.view.BaseView;
import com.mirhoseini.trakttv.view.adapter.PopularMoviesRecyclerViewAdapter;
import com.mirhoseini.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;
import tv.trakt.api.model.Movie;

/**
 * Created by Mohsen on 19/07/16.
 */

public class PopularMoviesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @Inject
    public PopularMoviesViewModel viewModel;
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

    @OnClick(R.id.no_internet)
    void onNoInternetClick(View view) {
        loadPopularMoviesData();
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

        // add material margins to list items card view
        recyclerView.addItemDecoration(new ItemSpaceDecoration(48));

        // allow pull to refresh on list
        swipeRefresh.setOnRefreshListener(this);

        // load data for first run
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

        initBindings();
    }

    private void initBindings() {

    }

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component
                .plus(new PopularMoviesModule())
                .inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;

        viewModel.destroy();
        viewModel = null;
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

    public void hideProgress() {
        progress.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
        progressMore.setVisibility(View.GONE);
    }

    public void showMessage(String message) {
        if (null != listener) {
            listener.showMessage(message);
        }
    }

    public void showOfflineMessage() {
        if (null != listener) {
            listener.showOfflineMessage();
        }

        if (null == adapter || adapter.getItemCount() == 0) {
            noInternet.setVisibility(View.VISIBLE);
        }
    }

    public void showConnectionError() {
        if (null != listener) {
            listener.showConnectionError();
        }
    }

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
        viewModel.loadPopularMoviesData(Utils.isConnected(context), page, Constants.PAGE_ROW_LIMIT);
    }

    private void loadMorePopularMoviesData(int newPage) {
        page = newPage;
        viewModel.loadPopularMoviesData(Utils.isConnected(context), page, Constants.PAGE_ROW_LIMIT);
    }

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
