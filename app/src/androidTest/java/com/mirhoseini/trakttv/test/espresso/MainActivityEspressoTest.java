package com.mirhoseini.trakttv.test.espresso;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.core.util.Constants;
import com.mirhoseini.trakttv.view.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import static org.junit.Assert.assertTrue;

/**
 * Created by Mohsen on 29/06/16.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    public static final String TEST = "Test";

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void pageLoadedRowCountTest() {
        onView(allOf(withId(R.id.list), isDisplayed()));

        RecyclerView recyclerView = (RecyclerView) activityRule.getActivity().findViewById(R.id.list);

        int rowCount = recyclerView.getAdapter().getItemCount();

        assertTrue(rowCount == Constants.PAGE_ROW_LIMIT);
    }

    @Test
    public void searchTest() {
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
        searchAutoComplete.perform(replaceText(TEST));

        // check if TEST movie is visible
        ViewInteraction titleTextView = onView(
                allOf(withId(R.id.title), withText(TEST), isDisplayed()));
        titleTextView.check(matches(withText(TEST)));

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
