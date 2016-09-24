package com.mirhoseini.trakttv.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.databinding.ActivityDetailsBinding;
import com.mirhoseini.trakttv.di.component.ApplicationComponent;
import com.mirhoseini.utils.Utils;

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

    private Movie movie;
    private ActivityDetailsBinding binding;

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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        Intent intent = getIntent();
        if (null != intent)
            movie = (Movie) intent.getSerializableExtra(ARG_MOVIE);
        else
            finish();

        binding.setMovie(movie);

        // inject views using ButterKnife
        ButterKnife.bind(this);

        setupToolbar();

        Timber.d("Details Activity Created");
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(movie.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);

        if (null == movie.getTrailer())
            menu.findItem(R.id.trailer).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.trailer:
                Utils.openWebsite(this, movie.getTrailer());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component.inject(this);
    }

}
