package com.codemitry.wearer.tests.splash

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.codemitry.wearer.R
import com.codemitry.wearer.activities.SplashActivity
import com.codemitry.wearer.app.TestApp
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SplashActivityNotSignedInInstrumentedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(SplashActivity::class.java)

    init {
        TestApp.signedIn = false
    }


    @Test
    fun goToSignInActivity() {

        Thread.sleep(500)

        onView(withId(R.id.signInInvitation)).check(matches(isDisplayed()))
        onView(withId(R.id.googleSignIn)).check(matches(isDisplayed()))
        onView(withId(R.id.anonymousSignIn)).check(matches(isDisplayed()))
    }
}