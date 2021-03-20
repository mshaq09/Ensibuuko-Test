package com.ensibuuko.test;

import android.content.Context;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EnsibuukoInstrumentedTest {


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testPostFlow() {

        onView(withText(R.string.tab_posts))
                .perform(click())
                .check(matches(isDisplayed()));

        onView(allOf(withId(R.id.title),withText("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))).perform(click());

    }


    @Test
    public void testUserFlow() {

        onView(withText(R.string.tab_my_posts))
                .perform(click())
                .check(matches(isDisplayed()));

        onView(allOf(withId(R.id.name),withText("Leanne Graham"))).perform(click());

        onView(withText(R.string.tab_user))
                .perform(click())
                .check(matches(isDisplayed()));

        onView(withText(R.string.tab_user_posts))
                .perform(click())
                .check(matches(isDisplayed()));

    }

    @Test
    public void testAlbumFlow() {

        onView(withText(R.string.tab_albums))
                .perform(click())
                .check(matches(isDisplayed()));

        onView(allOf(withId(R.id.title),withText("quidem molestiae enim"))).perform(click());

        onView(allOf(withId(R.id.item_title),withText("accusamus beatae ad facilis cum similique qui sunt"))).perform(click());



    }

}