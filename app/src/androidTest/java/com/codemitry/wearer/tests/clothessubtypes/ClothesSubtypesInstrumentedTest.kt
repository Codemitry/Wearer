package com.codemitry.wearer.tests.clothessubtypes

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.codemitry.wearer.R
import com.codemitry.wearer.activities.ClothesTypesByWearingWayActivity
import com.codemitry.wearer.app.TestApp
import com.codemitry.wearer.models.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ClothesSubtypesInstrumentedTest {


    @get:Rule
    var scenarioRule = ActivityScenarioRule(ClothesTypesByWearingWayActivity::class.java)

    private lateinit var clothesType: ClothesTypesByWearingWay

    init {
        TestApp.isDbManagerOk = true
    }

    @Before
    fun init() {
        clothesType = ClothesTypesByWearingWay.values().random()

        scenarioRule.scenario.onActivity { activity ->
            (activity.application as TestApp).clothesType = clothesType
        }

        onView(withText(clothesType.nameResource)).perform(click())
        onView(withId(R.id.addClothesSubtype)).check(matches(isDisplayed()))

        Thread.sleep(500)
    }

    @Test
    fun askOpenAddClothesSubtypes() {
        onView(withId(R.id.addClothesSubtype)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.clothesSubtypesList)).check(matches(isDisplayed()))

        pressBack()
        onView(withId(R.id.addClothesSubtype)).check(matches(isDisplayed()))
        Thread.sleep(1000)
    }

    private fun getWhereClickSubtype(pos: Int): String = when (clothesType) {
        ClothesTypesByWearingWay.OUTERWEAR -> OuterwearTypes.values()[pos]
        ClothesTypesByWearingWay.LIGHT_CLOTHES -> LightClothesTypes.values()[pos]
        ClothesTypesByWearingWay.UNDERWEAR -> UnderwearTypes.values()[pos]
        ClothesTypesByWearingWay.SHOES -> ShoesTypes.values()[pos]
        ClothesTypesByWearingWay.ACCESSORIES -> AccessoriesTypes.values()[pos]
    }.toString().toLowerCase().capitalize()

    @Test
    fun addClothesType() {
        onView(withId(R.id.addClothesSubtype)).perform(click())
        Thread.sleep(500)

        val whereClick = getWhereClickSubtype(0)

        onView(withText(whereClick)).perform(click())

        Thread.sleep(500)

        onView(withText(whereClick)).check(matches(isDisplayed()))
    }

    @Test
    fun addSomeClothesTypes() {
        val shuffledPos = listOf(0, 1, 2).shuffled().subList(0, (2..3).random())
        val added = mutableListOf<String>()
        for (i in shuffledPos) {
            onView(withId(R.id.addClothesSubtype)).perform(click())
            Thread.sleep(500)

            val whereClick = getWhereClickSubtype(i)
            added.add(whereClick)
            onView(withText(whereClick)).perform(click())
            Thread.sleep(500)

            for (item in added) {
                onView(withText(item)).check(matches(isDisplayed()))
            }
        }

//        onView(withText(whereClick)).perform(swipeLeft())
    }

    @Test
    fun addSomeClothesTypesAndDelete() {
        val added = mutableListOf<String>()
        val removed = mutableListOf<String>()

        // add
        // take 2 or 3 random first
        for (i in listOf(0, 1, 2).shuffled().take((2..3).random())) {
            onView(withId(R.id.addClothesSubtype)).perform(click())
            Thread.sleep(500)

            val whereClick = getWhereClickSubtype(i)
            added.add(whereClick)
            onView(withText(whereClick)).perform(click())
            Thread.sleep(500)

            for (item in added) {
                onView(withText(item)).check(matches(isDisplayed()))
            }
        }

        // remove
        // take random from 1 to size.added items in added
        for (item in added.shuffled().take((1 until added.size).random())) {
            onView(withText(item)).perform(swipeLeft())

            Thread.sleep(500)
            added.remove(item)
            removed.add(item)

            for (clothes in added) {
                onView(withText(clothes)).check(matches(isDisplayed()))
            }
            for (clothes in removed) {
                onView(withText(clothes)).check(doesNotExist())
            }

        }

    }


}