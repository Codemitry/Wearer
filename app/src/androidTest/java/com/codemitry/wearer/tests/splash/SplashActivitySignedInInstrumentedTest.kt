package com.codemitry.wearer.tests.splash

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
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
class SplashActivitySignedInInstrumentedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(SplashActivity::class.java)

    init {
        TestApp.signedIn = true
    }


    @Test
    fun goToClothesTypesByWearingWayActivity() {


        // --- start ClothesByWearingWay activity ---

        onView(withText(R.string.outerwear)).check(matches(isDisplayed()))
        onView(withText(R.string.lightClothes)).check(matches(isDisplayed()))
        onView(withText(R.string.underwear)).check(matches(isDisplayed()))
        onView(withText(R.string.shoes)).check(matches(isDisplayed()))
        onView(withText(R.string.accessories)).check(matches(isDisplayed()))

        onView(withId(R.id.outerwear)).check(matches(isDisplayed()))
        onView(withId(R.id.lightClothes)).check(matches(isDisplayed()))
        onView(withId(R.id.underwear)).check(matches(isDisplayed()))
        onView(withId(R.id.shoes)).check(matches(isDisplayed()))
        onView(withId(R.id.accessories)).check(matches(isDisplayed()))
    }
}