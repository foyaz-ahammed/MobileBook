package com.play2pay.bookapp.extensions

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers.not

private fun Int.onId() = onView(ViewMatchers.withId(this))

fun Int.isDisplayed(): ViewInteraction =
    this.onId().check(matches(ViewMatchers.isDisplayed()))

fun Int.isNotDisplayed(): ViewInteraction =
    this.onId().check(matches(not(ViewMatchers.isDisplayed())))

fun Int.hasText(text: String): ViewInteraction =
    this.onId().check(matches(ViewMatchers.withText(text)))