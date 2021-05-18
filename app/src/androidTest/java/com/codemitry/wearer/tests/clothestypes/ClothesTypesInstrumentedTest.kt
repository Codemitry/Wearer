package com.codemitry.wearer.tests.clothestypes

import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.codemitry.wearer.R
import com.codemitry.wearer.activities.ClothesTypesByWearingWayActivity
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ClothesTypesInstrumentedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(ClothesTypesByWearingWayActivity::class.java)


    @Test
    fun initialState() {

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


    @Test
    fun openAnyClothes() {
        val clothesType = ClothesTypesByWearingWay.values().random()

        onView(withText(clothesType.nameResource)).perform(click())

        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar))))
            .check(matches(withText(clothesType.nameResource)))
    }

    @Test
    fun openOuterwear() {
        onView(withText(R.string.outerwear)).perform(click())

        onView(withId(R.id.appBar)).check(matches(isDisplayed()))

        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar))))
            .check(matches(withText(R.string.outerwear)))

    }

    @Test
    fun openLightClothes() {
        onView(withText(R.string.lightClothes)).perform(click())

        onView(withId(R.id.appBar)).check(matches(isDisplayed()))

        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar))))
            .check(matches(withText(R.string.lightClothes)))

    }

    @Test
    fun openUnderwear() {
        onView(withText(R.string.underwear)).perform(click())

        onView(withId(R.id.appBar)).check(matches(isDisplayed()))

        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar))))
            .check(matches(withText(R.string.underwear)))

    }

    @Test
    fun openShoes() {
        onView(withText(R.string.shoes)).perform(click())

        onView(withId(R.id.appBar)).check(matches(isDisplayed()))

        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar))))
            .check(matches(withText(R.string.shoes)))

    }

    @Test
    fun openAccessories() {
        onView(withText(R.string.accessories)).perform(click())

        onView(withId(R.id.appBar)).check(matches(isDisplayed()))

        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar))))
            .check(matches(withText(R.string.accessories)))

    }

    @Test
    fun walkClothesSubtypes() {
        openOuterwear()

        pressBack()
        initialState()

        openLightClothes()

        pressBack()
        initialState()

        openUnderwear()

        pressBack()
        initialState()

        openShoes()

        pressBack()
        initialState()

        openAccessories()

        pressBack()
        initialState()
    }
}