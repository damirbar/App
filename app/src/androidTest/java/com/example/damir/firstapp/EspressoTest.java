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



    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =      //  This rule provides functional testing of a single Activity.
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void holdOn() throws InterruptedException {          //  Before runnimg the tests, we want the app to be on for sure.
        sleep(5000);
    }

    @Test
    public void signInButtonAppears() throws InterruptedException {         //  Checks the sign_in string appears on the first activity.
        onView((withText(R.string.sign_in))).check(matches(isDisplayed()));
    }

    @Test
    public void signUpButtonAppears() throws InterruptedException {         //  Checks the sign_up string appears on the first activity.
        onView((withText(R.string.sign_up))).check(matches(isDisplayed()));
    }

    @Test
    public void headerAppear() throws InterruptedException {                //  Checks the app_name string appears on the first activity.
        onView((withText(R.string.app_name))).check(matches(isDisplayed()));
    }

    @Test
    public void intentHappened() throws InterruptedException {              //  Checks that the user we added by the admin, succeeded loging in,
                                                                            //  and that the branches_text string appears on the GetLocation activity.
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
