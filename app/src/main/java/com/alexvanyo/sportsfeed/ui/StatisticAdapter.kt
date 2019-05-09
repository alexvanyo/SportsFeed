package com.alexvanyo.sportsfeed.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.databinding.StatisticItemBinding

class StatisticAdapter :
    ListAdapter<Competition.PairedStatistic, StatisticAdapter.DataBoundViewHolder<StatisticItemBinding>>(
        object : DiffUtil.ItemCallback<Competition.PairedStatistic>() {
            override fun areItemsTheSame(
                oldItem: Competition.PairedStatistic,
                newItem: Competition.PairedStatistic
            ): Boolean = oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: Competition.PairedStatistic,
                newItem: Competition.PairedStatistic
            ): Boolean = oldItem == newItem

        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<StatisticItemBinding> {
        return DataBoundViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.statistic_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<StatisticItemBinding>, position: Int) {
        holder.binding.pairedStatistic = getItem(position)
        holder.binding.executePendingBindings()
    }

    class DataBoundViewHolder<out T : ViewDataBinding> constructor(val binding: T) :
        RecyclerView.ViewHolder(binding.root)
}