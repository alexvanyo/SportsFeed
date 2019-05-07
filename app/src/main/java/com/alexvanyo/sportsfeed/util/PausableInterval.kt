package com.alexvanyo.sportsfeed.util

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

/**
 * Utility class that sets up a pausable, observable interval in [observable] that is initially paused.
 *
 * When paused (via [pause]), the interval will stop emitting ticks.
 *
 * Upon resuming (via [resume]), the interval will resume emitting ticks, and reset the interval if a tick was missed
 * during suspension.
 */
class PausableInterval(private val systemTime: () -> Long, private val period: Long, private val unit: TimeUnit) {

    private val lastExecution = AtomicLong()
    private val shouldTick = AtomicBoolean()
    private val newIntervalNotifier = PublishSubject.create<Unit>()

    val observable: Observable<Long> = newIntervalNotifier
        .switchMap { Observable.interval(0, period, unit) }
        .filter {
            shouldTick.get()
        }
        .map {
            // Intercept the tick to update the last execution
            it.also { lastExecution.set(systemTime()) }
        }

    init {
        lastExecution.set(0)
        shouldTick.set(false)
    }

    /**
     * Pauses the interval.
     */
    fun pause() = shouldTick.set(false)

    /**
     * Resumes the interval. If the pause caused a tick to be skipped, the interval is reset to start immediately.
     */
    fun resume() {
        shouldTick.set(true)

        // Check if we missed a scheduled tick
        if (unit.convert(systemTime() - lastExecution.get(), TimeUnit.MILLISECONDS) > period) {
            // If we did, restart the interval with an immediate event
            newIntervalNotifier.onNext(Unit)
        }
    }
}