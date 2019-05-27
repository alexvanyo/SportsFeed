package com.alexvanyo.sportsfeed.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.api.ScoreboardData
import com.alexvanyo.sportsfeed.repository.FeedRepository
import com.alexvanyo.sportsfeed.util.PausableObservable
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

private const val LOG_TAG = "FeedViewModel"
private const val SELECTED_COMPETITION_KEY = "selected_competition_key"

/**
 * Main view model for the feed.
 */
@AutoFactory(implementing = [ViewModelFactory::class])
class FeedViewModel constructor(
    @Provided private val feedRepository: FeedRepository,
    @Provided private val pausableObservable: PausableObservable<*>,
    private val handle: SavedStateHandle
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var competitionMap: HashMap<String, Competition> = HashMap()

    // Override inactive/active callbacks to pause/resume network polling
    private val _competitions: MutableLiveData<List<Competition>> = object : MutableLiveData<List<Competition>>() {
        override fun onInactive() {
            super.onInactive()
            // Don't go inactive if the sibling live data is being observed
            if (!_selectedCompetition.hasActiveObservers()) {
                pausableObservable.pause()
            }
        }

        override fun onActive() {
            super.onActive()
            pausableObservable.resume()
        }
    }

    val competitions: LiveData<List<Competition>>
        get() = _competitions

    private val _selectedCompetition: MutableLiveData<Competition> = object : MutableLiveData<Competition>() {
        override fun onInactive() {
            super.onInactive()
            // Don't go inactive if the sibling live data is being observed
            if (!_competitions.hasActiveObservers()) {
                pausableObservable.pause()
            }
        }

        override fun onActive() {
            super.onActive()
            pausableObservable.resume()
        }
    }

    val selectedCompetition: LiveData<Competition>
        get() = _selectedCompetition


    private val dataFetchErrorNotifier = PublishSubject.create<Unit>()

    /**
     * Unit observable that will notify that a network request failed.
     */
    val dataFetchErrorObservable: Observable<Unit> = dataFetchErrorNotifier

    init {
        compositeDisposable.add(pausableObservable.observable.subscribe {
            compositeDisposable.add(
                feedRepository.getScoreboardData()
                    .subscribeOn(Schedulers.io())
                    .subscribe(::handleScoreboardData, ::handleScoreboardDataError)
            )
        }
        )
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }

    /**
     * Helper function for handling new scoreboard data
     */
    private fun handleScoreboardData(scoreboardData: ScoreboardData) {
        scoreboardData.events.flatMap { it.competitions }.forEach { competitionMap[it.uid] = it }

        _competitions.postValue(competitionMap.values.toList())

        if (competitionMap.containsKey(_selectedCompetition.value?.uid)) {
            _selectedCompetition.postValue(competitionMap[_selectedCompetition.value?.uid])
        }
    }

    /**
     * Helper function for handling a scoreboard error
     */
    private fun handleScoreboardDataError(error: Throwable) {
        Log.d(LOG_TAG, "Error fetching scoreboard data", error)
        dataFetchErrorNotifier.onNext(Unit)
    }

    /**
     * Selects a specific competition from the full list.
     *
     * @param uid the unique string identifier corresponding to the competition
     */
    fun selectCompetition(uid: String) = _selectedCompetition.postValue(competitionMap[uid])

    /**
     * Restores state from the given bundle.
     *
     * @param savedInstanceState the bundle to restore from.
     */
    fun restoreState(savedInstanceState: Bundle) {
        if (savedInstanceState.containsKey(SELECTED_COMPETITION_KEY)) {
            _selectedCompetition.postValue(savedInstanceState.getParcelable(SELECTED_COMPETITION_KEY))
        }
    }

    /**
     * Saves state into the given bundle.
     *
     * @param outState the bundle to save into
     */
    fun saveState(outState: Bundle) {
        outState.putParcelable(SELECTED_COMPETITION_KEY, _selectedCompetition.value)
    }
}