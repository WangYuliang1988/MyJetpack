package com.yigelangzi.jetpack

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Aliases the current default Android JUnit 4 class runner, for future-proofing.
// If future versions of JUnit change the default Runner class, they will also
// change the definition of this class. Developers wanting to explicitly tag a class
// as an Android JUnit 4 class should use @RunWith(AndroidJUnit4.class)
@RunWith(AndroidJUnit4::class)
class GardenActivityTest {

    // @JvmField: Instructs the Kotlin compiler not to generate getters/setters for this property and expose it as a field.
    // @JvmStatic: Specifies that an additional static method needs to be generated from this element if it's a function. If this
    // element is a property, additional static getter/setter methods should be generated.
    @Rule @JvmField
    // 此处涉及TestRule知识，详见：https://developer.android.com/training/testing/junit-rules
    var activityTestRule = ActivityTestRule(GardenActivity::class.java)

    @Test
    fun clickAddPlant_openPlantList() {

        // When the "Add Plant" button is clicked
        onView(withId(R.id.add_plant)).perform(click())

        // Then the ViewPager should change to the Plant List page
        onView(withId(R.id.plant_list)).check(matches(isDisplayed()))
    }
}
