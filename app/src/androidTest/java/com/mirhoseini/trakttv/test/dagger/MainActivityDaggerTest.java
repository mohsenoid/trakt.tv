package com.mirhoseini.trakttv.test.dagger;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.core.service.TraktApi;
import com.mirhoseini.trakttv.test.dagger.di.TestApplicationComponent;
import com.mirhoseini.trakttv.view.activity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import rx.Observable;
import tv.trakt.api.model.Movie;
import tv.trakt.api.model.SearchMovieResult;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityDaggerTest {

    public static final String TEST_MOVIE_TITLE = "Test Movie";
    public static final String TEST_MOVIE_OVERVIEW = "Test Movie Overview";

    public static final String TEST_SEARCH_MOVIE_TITLE = "Test Search Movie";


    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(
            MainActivity.class,
            true,
            false);   // do not launch the activity immediately

    @Inject
    TraktApi api;

    Movie[] expectedMovieResult;
    SearchMovieResult[] expectedSearchResult;

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MockTraktApplication app = (MockTraktApplication) instrumentation.getTargetContext().getApplicationContext();
        TestApplicationComponent component = (TestApplicationComponent) app.getComponent();
        component.inject(this);

        // Set up the stub we want to return in the mock
        Movie movie = new Movie();
        movie.setTitle(TEST_MOVIE_TITLE);
        movie.setOverview(TEST_MOVIE_OVERVIEW);

        expectedMovieResult = new Movie[1];
        expectedMovieResult[0] = movie;

        // Set up the mock
        when(api.getPopularMovies(any(Integer.class), any(Integer.class), any(String.class)))
                .thenReturn(Observable.just(expectedMovieResult));
    }

    @Test
    public void shouldBeAbleToMockThePopularMovies() {
        // Launch the activity
        mainActivity.launchActivity(new Intent());

        // Check that the view is what we expect it to be
        onView(withId(R.id.title)).check(matches(withText(TEST_MOVIE_TITLE)));
        onView(withId(R.id.overview)).check(matches(withText(TEST_MOVIE_OVERVIEW)));
    }

    @Test
    public void shouldBeAbleToMockTheSearchMovies() {
        // Set up the stub we want to return in the mock
        Movie movie = new Movie();
        movie.setTitle(TEST_SEARCH_MOVIE_TITLE);
        movie.setOverview(TEST_MOVIE_OVERVIEW);

        expectedSearchResult = new SearchMovieResult[1];
        SearchMovieResult searchMovieResult = new SearchMovieResult();
        searchMovieResult.setMovie(movie);

        expectedSearchResult[0] = searchMovieResult;

        // Set up the mock
        when(api.searchMovies(any(String.class), any(Integer.class), any(Integer.class), any(String.class)))
                .thenReturn(Observable.just(expectedSearchResult));

        // Launch the activity
        mainActivity.launchActivity(new Intent());

        // click on Search icon
        ViewInteraction searchImageView = onView(
                allOf(withId(R.id.search_button), withContentDescription("Search"),
                        withParent(allOf(withId(R.id.search_bar),
                                withParent(withId(R.id.action_search)))),
                        isDisplayed()));
        searchImageView.perform(click());

        // check if No Result Found! is displayed
        ViewInteraction noResultFoundTextView = onView(
                allOf(withId(R.id.no_result_found), withText("No Result Found!"), isDisplayed()));
        noResultFoundTextView.check(matches(withText("No Result Found!")));

        // search for TEST
        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText(TEST_SEARCH_MOVIE_TITLE));

        // check if TEST movie is visible
        ViewInteraction titleTextView = onView(
                allOf(withId(R.id.title), withText(TEST_SEARCH_MOVIE_TITLE), isDisplayed()));
        titleTextView.check(matches(withText(TEST_SEARCH_MOVIE_TITLE)));

        // perform clear search query
        ViewInteraction clearImageView = onView(
                allOf(withId(R.id.search_close_btn), withContentDescription("Clear query"),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        clearImageView.perform(click());

        // check if No Result Found! is displayed again
        ViewInteraction anotherNoResultFoundTextView = onView(
                allOf(withId(R.id.no_result_found), withText("No Result Found!"), isDisplayed()));
        anotherNoResultFoundTextView.check(matches(withText("No Result Found!")));

    }
}

