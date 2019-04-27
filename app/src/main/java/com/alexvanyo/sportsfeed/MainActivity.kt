package com.alexvanyo.sportsfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.*
import com.alexvanyo.sportsfeed.databinding.GameItemBinding

class TextAdapter : RecyclerView.Adapter<TextAdapter.DataBoundViewHolder<GameItemBinding>>() {

    val sortedList: SortedList<String>

    init {
        sortedList = SortedList(String::class.java, object : SortedListAdapterCallback<String>(this) {
            override fun compare(o1: String?, o2: String?): Int {
                return if (o1 == null && o2 == null) {
                    0
                } else if (o1 == null) {
                    -1
                } else if (o2 == null) {
                    1
                } else {
                    o1.compareTo(o2)
                }
            }

            override fun areContentsTheSame(oldItem: String?, newItem: String?): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(item1: String?, item2: String?): Boolean {
                return false
            }

        })
    }

    override fun getItemCount() = sortedList.size()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<GameItemBinding> {
        return DataBoundViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.game_item, parent, false))
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<GameItemBinding>, position: Int) {
        holder.binding.text = sortedList.get(position)
        holder.binding.executePendingBindings()
    }

    class DataBoundViewHolder<out T : ViewDataBinding> constructor(val binding: T) :
        RecyclerView.ViewHolder(binding.root)
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
