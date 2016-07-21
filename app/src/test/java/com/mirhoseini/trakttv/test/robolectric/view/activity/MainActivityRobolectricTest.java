package com.mirhoseini.trakttv.test.robolectric.view.activity;

import com.mirhoseini.trakttv.BuildConfig;
import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.test.robolectric.support.ShadowSnackbar;
import com.mirhoseini.trakttv.view.activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static com.mirhoseini.trakttv.test.robolectric.support.Assert.assertAlertDialogIsShown;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;


/**
 * Created by Mohsen on 20/07/16.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows = {ShadowSnackbar.class})
public class MainActivityRobolectricTest {

    final static String TEST_TEXT = "This is a test text.";
    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(MainActivity.class);
        assertNotNull(activity);
    }

    @Test
    public void testShowToastMessage() throws Exception {
        activity.showMessage(TEST_TEXT);
        assertThat(TEST_TEXT, equalTo(ShadowToast.getTextOfLatestToast()));
    }

    @Test
    public void testShowInternetConnectionError() throws Exception {
        activity.showConnectionError();

        assertAlertDialogIsShown(R.string.no_connection_title, R.string.no_connection);
    }

    @Test
    public void testShowDoubleInternetConnectionError() throws Exception {
        activity.showConnectionError();
        activity.showConnectionError();

        assertAlertDialogIsShown(R.string.no_connection_title, R.string.no_connection);
    }

}
