package com.alexvanyo.sportsfeed

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.feed_fragment.*

class FeedFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textAdapter = TextAdapter()

        textAdapter.sortedList.addAll((1..100).map { i -> "Test $i" }.toList().shuffled())

        this.recycler_view.apply {
            setHasFixedSize(true)
            adapter = textAdapter
        }

        val random = java.util.Random()
        val handler = Handler()

        val runnable = object : Runnable {
            override fun run() {
                textAdapter.sortedList.add("Test " + random.nextInt(100))
                handler.postDelayed(this, 200)
            }
        }

        handler.post(runnable)
    }
}