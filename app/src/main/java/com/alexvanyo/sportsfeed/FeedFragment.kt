package com.alexvanyo.sportsfeed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexvanyo.sportsfeed.api.ESPNService
import com.alexvanyo.sportsfeed.api.ScoreboardData
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.feed_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FeedFragment : DaggerFragment() {

    @Inject
    lateinit var espnService: ESPNService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textAdapter = TextAdapter()

        this.recycler_view.apply {
            setHasFixedSize(true)
            adapter = textAdapter
        }

        val gamesCall: Call<ScoreboardData> = espnService.getMLBGames()
        gamesCall.enqueue(object : Callback<ScoreboardData> {
            override fun onFailure(call: Call<ScoreboardData>?, t: Throwable?) {
                Log.d("FeedFragment", "API call failed")
                throw t!!
            }

            override fun onResponse(call: Call<ScoreboardData>, response: Response<ScoreboardData>) {
                Log.d("FeedFragment", "Response ${response!!.body().toString()}")
                textAdapter.sortedList.addAll(response.body()!!.events.map { event -> event.name })
            }

        })
    }
}