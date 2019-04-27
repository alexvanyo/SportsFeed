package com.alexvanyo.sportsfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.alexvanyo.sportsfeed.databinding.GameItemBinding

class TextAdapter : RecyclerView.Adapter<TextAdapter.DataBoundViewHolder<GameItemBinding>>() {

    val sortedList: SortedList<String>

    init {
        sortedList = SortedList(String::class.java, object : SortedListAdapterCallback<String>(this) {
            override fun compare(o1: String?, o2: String?): Int {
                // Sort lexicographically, with null coming first
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