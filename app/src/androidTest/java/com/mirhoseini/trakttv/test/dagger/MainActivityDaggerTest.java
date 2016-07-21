package com.mirhoseini.trakttv.test.dagger;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.core.service.TraktApi;
import com.mirhoseini.trakttv.core.util.SchedulerProvider;
import com.mirhoseini.trakttv.test.dagger.di.TestApplicationComponent;
import com.mirhoseini.trakttv.view.activity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import rx.Observable;
import tv.trakt.api.model.Movie;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityDaggerTest {

    public static final String TEST_MOVIE_TITLE = "Test Movie";
    public static final String TEST_MOVIE_OVERVIEW = "Test Movie Overview";
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(
            MainActivity.class,
            true,
            false);   // do not launch the activity immediately

    @Inject
    SchedulerProvider scheduler;

    @Inject
    TraktApi api;

    Movie[] expectedResult;

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MockTraktApplication app = (MockTraktApplication) instrumentation.getTargetContext().getApplicationContext();
        TestApplicationComponent component = (TestApplicationComponent) app.getComponent();
        component.inject(this);
    }

    @Test
    public void shouldBeAbleToMockThePopularMovies() {
        // Set up the stub we want to return in the mock
        Movie movie = new Movie();
        movie.setTitle(TEST_MOVIE_TITLE);
        movie.setOverview(TEST_MOVIE_OVERVIEW);

        expectedResult = new Movie[1];
        expectedResult[0] = movie;

        // Set up the mock
        when(api.getPopularMovies(any(Integer.class), any(Integer.class), any(String.class)))
                .thenReturn(Observable.just(expectedResult));

        // Launch the activity
        mainActivity.launchActivity(new Intent());

        // Check that the view is what we expect it to be
        onView(withId(R.id.title)).check(matches(withText(TEST_MOVIE_TITLE)));
        onView(withId(R.id.overview)).check(matches(withText(TEST_MOVIE_OVERVIEW)));
    }
}

