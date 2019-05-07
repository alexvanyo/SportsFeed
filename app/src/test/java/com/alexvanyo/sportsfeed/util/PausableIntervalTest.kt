package com.alexvanyo.sportsfeed.util

import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

private const val TEST_PERIOD = 60L

class PausableIntervalTest {
    @get:Rule
    val testSchedulerRule = TestSchedulerRule()

    private var timeInMillis = System.currentTimeMillis()
    private val pausableInterval = PausableInterval({ timeInMillis }, TEST_PERIOD, TimeUnit.SECONDS)
    private val testObserver = pausableInterval.observable.test()

    fun advanceTime(duration: Long) {
        // The artificial time must be updated first, so that it will be seen
        timeInMillis += TimeUnit.MILLISECONDS.convert(duration, TimeUnit.SECONDS)
        testSchedulerRule.testScheduler.advanceTimeBy(duration, TimeUnit.SECONDS)
    }

    @Test
    fun `observable is initially paused`() {
        testObserver.assertValueCount(0)

        advanceTime(10 * TEST_PERIOD)
        testObserver.assertValueCount(0)
    }

    @Test
    fun `observable is an interval when resumed`() {
        pausableInterval.resume()
        testSchedulerRule.testScheduler.triggerActions()

        testObserver.assertValueCount(1)

        for (i in (2..10)) {
            advanceTime(TEST_PERIOD)
            testObserver.assertValueCount(i)
        }
    }

    @Test
    fun `observable pauses correctly after being resumed`() {
        pausableInterval.resume()
        advanceTime(5 * TEST_PERIOD)
        pausableInterval.pause()
        advanceTime(5 * TEST_PERIOD)
        // 1 for the initial execution, and 5 for each minute
        testObserver.assertValueCount(6)
    }

    @Test
    fun `observable resumes correctly after being paused and resumed`() {
        pausableInterval.resume()
        advanceTime(5 * TEST_PERIOD)
        pausableInterval.pause()
        advanceTime(5 * TEST_PERIOD)
        pausableInterval.resume()
        advanceTime(5 * TEST_PERIOD)
        // 2 for each initial execution, and 10 for each minute
        testObserver.assertValueCount(12)
    }

    @Test
    fun `observable doesn't request more frequently than duration`() {
        pausableInterval.resume()
        advanceTime(2 * TEST_PERIOD)
        pausableInterval.pause()
        advanceTime(TEST_PERIOD / 2)
        pausableInterval.resume()
        testSchedulerRule.testScheduler.triggerActions()
        // If we were unpaused, we wouldn't have made a tick yet, so we should still be at 3
        testObserver.assertValueCount(3)

        advanceTime(TEST_PERIOD / 2)
        // We should still be on the same schedule as before
        testObserver.assertValueCount(4)
    }

    @Test
    fun `observable changes interval`() {
        pausableInterval.resume()
        advanceTime(2 * TEST_PERIOD)
        pausableInterval.pause()
        advanceTime(3 * TEST_PERIOD / 2)
        pausableInterval.resume()
        testSchedulerRule.testScheduler.triggerActions()
        // We should immediately have a tick
        testObserver.assertValueCount(4)

        advanceTime(TEST_PERIOD / 2)
        // We should be on a new schedule, so we should still be at 4
        testObserver.assertValueCount(4)

        advanceTime(TEST_PERIOD / 2)
        testObserver.assertValueCount(5)
    }
}