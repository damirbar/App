package com.example.damir.firstapp;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;

/**
 * Created by damir on 9/12/2017.
 *
 * Espresso test, for graphic tests.
 */

public class EspressoTest {



    //  This rule provides functional testing of a single Activity.
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    //  Before runnimg the tests, we want the app to be on for sure.
    @Before
    public void holdOn() throws InterruptedException {
        sleep(5000);
    }

    //  Checks the sign_in string appears on the first activity.
    @Test
    public void signInButtonAppears() throws InterruptedException {
        onView((withText(R.string.sign_in))).check(matches(isDisplayed()));
    }

    //  Checks the sign_up string appears on the first activity.
    @Test
    public void signUpButtonAppears() throws InterruptedException {
        onView((withText(R.string.sign_up))).check(matches(isDisplayed()));
    }

    //  Checks the app_name string appears on the first activity.
    @Test
    public void headerAppear() throws InterruptedException {
        onView((withText(R.string.app_name))).check(matches(isDisplayed()));
    }

    //  Checks that the user we added by the admin, succeeded loging in, and that the branches_text
    //  string appears on the GetLocation activity.
    @Test
    public void intentHappened() throws InterruptedException {
        onView(withId(R.id.name_field)).perform(typeText("admn"));
        sleep(500);
        pressBack();
        sleep(500);
        onView(withId(R.id.pass_field)).perform(typeText("admn"));
        sleep(500);
        pressBack();
        sleep(500);
        onView(withId(R.id.signin_button)).perform(click());
        sleep(5000);
        onView(withText(R.string.branches_text)).check(matches(isDisplayed()));
        sleep(2000);

    }
}
