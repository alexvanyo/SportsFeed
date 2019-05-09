package com.alexvanyo.sportsfeed.ui

import androidx.recyclerview.widget.DiffUtil
import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.databinding.StatisticItemBinding
import com.alexvanyo.sportsfeed.util.DataBoundListAdapter

class StatisticAdapter : DataBoundListAdapter<Competition.PairedStatistic, StatisticItemBinding>(
    R.layout.statistic_item,
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
    override fun bind(binding: StatisticItemBinding, item: Competition.PairedStatistic) {
        binding.pairedStatistic = item
    }
}