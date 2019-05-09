package com.alexvanyo.sportsfeed.util

import io.reactivex.Observable

/**
 * An observable that can be paused and resumed.
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