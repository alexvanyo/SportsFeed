package com.alexvanyo.sportsfeed.util

import org.mockito.Mockito

/**
 * A mock that handles generics
 * @see <a href="https://discuss.kotlinlang.org/t/kotlin-unit-test-failing-when-using-generics-and-mockito/1866">Issue</a>
 */
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)