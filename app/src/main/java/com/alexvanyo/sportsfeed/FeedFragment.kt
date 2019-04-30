package com.alexvanyo.sportsfeed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexvanyo.sportsfeed.api.ESPNService
import com.alexvanyo.sportsfeed.api.ScoreboardData
import kotlinx.android.synthetic.main.feed_fragment.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FeedFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textAdapter = TextAdapter()

        this.recycler_view.apply {
            setHasFixedSize(true)
            adapter = textAdapter
        }

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(logging)

        val theESPNService: ESPNService = Retrofit.Builder()
            .baseUrl("http://site.api.espn.com/apis/site/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()
            .create(com.alexvanyo.sportsfeed.api.ESPNService::class.java)

        val gamesCall: Call<ScoreboardData> = theESPNService.getMLBGames()
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