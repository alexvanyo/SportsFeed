package com.alexvanyo.sportsfeed.util

import io.reactivex.Observable

/**
 * An that can be paused and
 */
interface PausableObservable<T> {

    val observable: Observable<T>

    /**
     * Pauses the interval.
     */
    fun pause()

    /**
     * Resumes the interval.
     */
    fun resume()
}