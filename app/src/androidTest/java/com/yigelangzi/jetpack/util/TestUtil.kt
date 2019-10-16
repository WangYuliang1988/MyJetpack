package com.yigelangzi.jetpack.util

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import com.yigelangzi.jetpack.data.GardenPlanting
import com.yigelangzi.jetpack.data.Plant
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH
import java.util.Calendar.SEPTEMBER
import java.util.Calendar.YEAR
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * [Plant] objects used for tests.
 */
val testPlants = arrayListOf(
    Plant("malus-pumila", "Apple", "Description Apple", 1),
    Plant("2", "B", "Description B", 1),
    Plant("3", "C", "Description C", 2)
)
val testPlant = testPlants[0]

/**
 * [Calendar] object used for tests.
 */
val testCalendar: Calendar = Calendar.getInstance().apply {
    set(YEAR, 1998)
    set(MONTH, SEPTEMBER)
    set(DAY_OF_MONTH, 9)
}

/**
 * [GardenPlanting] object used for tests.
 */
val testGardenPlanting = GardenPlanting(testPlant.plantId, testCalendar, testCalendar)

/**
 * Simplify testing Intents with Chooser
 *
 * @param matcher the actual intent before wrapped by Chooser Intent
 */
fun chooser(matcher: Matcher<Intent>): Matcher<Intent> = allOf(
    hasAction(Intent.ACTION_CHOOSER),
    hasExtra(`is`(Intent.EXTRA_INTENT), matcher)
)

/**
 * Helper method for testing LiveData objects.
 *
 * Get the value from a LiveData object. We're waiting for LiveData to emit, for 2 seconds.
 * Once we got a notification via onChanged, we stop observing.
 */
@Throws(InterruptedException::class)
fun <T> getValue(liveData: LiveData<T>): T {
    val data = arrayOfNulls<Any>(1)
    // 此处涉及CountDownLatch知识，详见：https://www.baeldung.com/java-countdown-latch
    val latch = CountDownLatch(1)
    liveData.observeForever { o ->
        data[0] = o
        latch.countDown()
    }
    latch.await(2, TimeUnit.SECONDS)
    return data[0] as T
}