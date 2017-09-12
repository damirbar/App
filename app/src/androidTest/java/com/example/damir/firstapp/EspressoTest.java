package com.example.damir.firstapp;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;

/**
 * Created by damir on 9/12/2017.
 */

public class EspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRyle =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void appNameTest() {
//        onView(withId(R.id.name_field)).check(matches(withText("Name Field")));
//        onView(withId(R.id.name_field)).check(matches(withText("Name Field")));
        onView(withId(R.id.signin_button)).perform(click());
//        onView(withText("Fill username and password")).check(matches(isDisplayed()));


//        onView(withId(R.id.name_field)).perform()

    }


}
