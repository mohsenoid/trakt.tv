package com.mirhoseini.trakttv.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.di.component.ApplicationComponent;
import com.mirhoseini.trakttv.view.fragment.DetailsFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import tv.trakt.api.model.Movie;

public class DetailsActivity extends BaseActivity {

    private static final String ARG_MOVIE = "movie";
    public static final String TAG_DETAILS_FRAGMENT = "details_fragment";

    // injecting dependencies via Dagger
    @Inject
    Context context;
    @Inject
    Resources resources;

    // injecting views via ButterKnife
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private DetailsFragment detailsFragment;

    private Movie movie;

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(ARG_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fixing the delayed loading problem
//        ActivityCompat.postponeEnterTransition(this);

        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        if (null != intent)
            movie = (Movie) intent.getSerializableExtra(ARG_MOVIE);
        else
            finish();

        // inject views using ButterKnife
        ButterKnife.bind(this);

        setupToolbar();

        Timber.d("Details Activity Created");
    }

    @Override
    protected void onResume() {
        super.onResume();

        FragmentManager fragmentManager = getSupportFragmentManager();
        detailsFragment = (DetailsFragment) fragmentManager.findFragmentByTag(TAG_DETAILS_FRAGMENT);


        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (null == detailsFragment) {
            createFragments();
        }

        attachFragments();

        Timber.d("Details Activity Resumed");
    }

    private void createFragments() {
        detailsFragment = DetailsFragment.newInstance(movie);
    }

    private void attachFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.details_fragment, detailsFragment, TAG_DETAILS_FRAGMENT);
        fragmentTransaction.commitAllowingStateLoss();
    }


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.while_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component.inject(this);
    }

}
