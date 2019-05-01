package com.alexvanyo.sportsfeed.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.api.Event
import com.alexvanyo.sportsfeed.databinding.EventItemBinding

class EventAdapter : RecyclerView.Adapter<EventAdapter.DataBoundViewHolder<EventItemBinding>>() {

    val sortedList: SortedList<Event>

    init {
        sortedList = SortedList(Event::class.java, object : SortedListAdapterCallback<Event>(this) {
            override fun compare(o1: Event?, o2: Event?): Int {
                // Sort lexicographically, with null coming first
                return if (o1 == null && o2 == null) {
                    0
                } else if (o1 == null) {
                    -1
                } else if (o2 == null) {
                    1
                } else {
                    o1.name.compareTo(o2.name)
                }
            }

            override fun areContentsTheSame(oldItem: Event?, newItem: Event?): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(item1: Event?, item2: Event?): Boolean {
                return false
            }

        })
    }

    override fun getItemCount() = sortedList.size()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<EventItemBinding> {
        return DataBoundViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.event_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<EventItemBinding>, position: Int) {
        holder.binding.text = sortedList.get(position).name
        holder.binding.executePendingBindings()
    }

    class DataBoundViewHolder<out T : ViewDataBinding> constructor(val binding: T) :
        RecyclerView.ViewHolder(binding.root)
}