package com.mirhoseini.trakttv.test.robolectric.view.fragment;

import android.view.View;

import com.mirhoseini.trakttv.BuildConfig;
import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.test.robolectric.support.ShadowSnackbar;
import com.mirhoseini.trakttv.view.activity.MainActivity;
import com.mirhoseini.trakttv.view.fragment.PopularMoviesFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.mirhoseini.trakttv.test.robolectric.support.Assert.assertSnackbarIsShown;
import static com.mirhoseini.trakttv.test.robolectric.support.Assert.assertViewIsNotVisible;
import static com.mirhoseini.trakttv.test.robolectric.support.Assert.assertViewIsVisible;
import static com.mirhoseini.trakttv.test.robolectric.support.ViewLocator.getView;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Mohsen on 20/07/16.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows = {ShadowSnackbar.class})
public class PopularMoviesFragmentRobolectricTest {

    private MainActivity activity;
    private PopularMoviesFragment fragment;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(MainActivity.class);
        fragment = (PopularMoviesFragment) activity.getSupportFragmentManager().findFragmentByTag(activity.TAG_POPULAR_MOVIES_FRAGMENT);
        assertNotNull(activity);
    }

    @Test
    public void testShowProgress() throws Exception {
        fragment.showProgress();

        View progress = getView(fragment, R.id.progress);
        assertViewIsNotVisible(progress);

        View progressMore = getView(fragment, R.id.progress_more);
        assertViewIsVisible(progressMore);

        View noInternet = getView(fragment, R.id.no_internet);
        assertViewIsNotVisible(noInternet);
    }

    @Test
    public void testHideProgress() throws Exception {
        fragment.hideProgress();

        View progress = getView(fragment, R.id.progress);
        assertViewIsNotVisible(progress);

        View progressMore = getView(fragment, R.id.progress_more);
        assertViewIsNotVisible(progressMore);

    }

    @Test
    public void testShowOfflineMessage() throws Exception {
        fragment.showOfflineMessage();

        assertSnackbarIsShown(R.string.offline_message);
    }

    @Test
    public void testShowRetryMessage() throws Exception {
        fragment.showRetryMessage(new Throwable("Well known error!"));

        assertSnackbarIsShown(R.string.retry_message);
    }

    @Test
    public void testOnDestroy() throws Exception {
        fragment.onDetach();

        assertNull(fragment.viewModel);
    }

}