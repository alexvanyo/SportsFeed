package com.alexvanyo.sportsfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.api.ESPNService
import com.alexvanyo.sportsfeed.api.ScoreboardData
import com.alexvanyo.sportsfeed.util.PausableObservable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Main view model for the feed.
 */
class FeedViewModel @Inject constructor(
    private val espnService: ESPNService,
    private val pausableObservable: PausableObservable<*>
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var competitionMap: Map<String, Competition> = emptyMap()

    init {
        compositeDisposable.add(pausableObservable.observable
            .flatMap { espnService.getMLBGames().onErrorResumeNext(Observable.empty()) }
            .subscribeOn(Schedulers.io())
            .subscribe(::handleScoreboardData))
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }

    /**
     * Helper function for handling new scoreboard data
     */
    private fun handleScoreboardData(scoreboardData: ScoreboardData) {
        val competitionList = scoreboardData.events.flatMap { it.competitions }

        competitionMap = competitionList.toHashSet().associateBy { it.uid }

        _competitions.postValue(competitionList)

        if (competitionMap.containsKey(_selectedCompetition.value?.uid)) {
            _selectedCompetition.postValue(competitionMap[selectedCompetition.value?.uid])
        }
    }

    /**
     * Selects a specific competition from the full list.
     *
     * @param uid the unique string identifier corresponding to the competition
     */
    fun selectCompetition(uid: String) = _selectedCompetition.postValue(competitionMap[uid])

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

}