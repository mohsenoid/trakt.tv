package com.mirhoseini.trakttv.view.activity;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.di.component.ApplicationComponent;
import com.mirhoseini.trakttv.view.fragment.PopularMoviesFragment;
import com.mirhoseini.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import tv.trakt.api.model.Movie;

/**
 * Created by Mohsen on 19/07/16.
 */

public class MainActivity extends BaseActivity implements PopularMoviesFragment.OnListFragmentInteractionListener {

    public static final String TAG_POPULAR_MOVIES_FRAGMENT = "popular_movies_fragment";

    // injecting dependencies via Dagger
    @Inject
    Context context;
    @Inject
    Resources resources;

    // injecting views via ButterKnife
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    AlertDialog internetConnectionDialog;
    private PopularMoviesFragment popularMoviesFragment;

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inject views using ButterKnife
        ButterKnife.bind(this);

        setupToolbar();

        Timber.d("Main Activity Created");
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.while_logo);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FragmentManager fragmentManager = getSupportFragmentManager();
        popularMoviesFragment = (PopularMoviesFragment) fragmentManager.findFragmentByTag(TAG_POPULAR_MOVIES_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (popularMoviesFragment == null) {
            createFragments();
        }

        showFragments();

        Timber.d("Activity Resumed");
    }

    private void createFragments() {
        popularMoviesFragment = PopularMoviesFragment.newInstance();
        popularMoviesFragment.setRetainInstance(true);
    }

    private void showFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.popular_movies_fragment, popularMoviesFragment, TAG_POPULAR_MOVIES_FRAGMENT);
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void onListFragmentInteraction(Movie movie) {
//        Intent movieIntent = MovieActivity.newIntent(context, movie);
//        startActivity(movie);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        presenter = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                // FIXME: 19/07/16 ???
                searchView = (SearchView) searchItem.getActionView();
            }
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Timber.i("onQueryTextChange: %s", newText);

                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Timber.i("onQueryTextSubmit: %s", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // FIXME: 19/07/16  Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMessage(String message) {
        Timber.d("Showing Message: %s", message);

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showOfflineMessage() {

    }

    @Override
    public void showConnectionError() {
        Timber.d("Showing Connection Error Message");

        hideInternetConnectionError();

        internetConnectionDialog = Utils.showNoInternetConnectionDialog(this, true);
    }

    public void hideInternetConnectionError() {
        if (internetConnectionDialog != null)
            internetConnectionDialog.dismiss();
    }

}