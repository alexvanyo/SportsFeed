package com.alexvanyo.sportsfeed.util

import org.mockito.Mockito

/**
 * A mock that handles generics
 */
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)