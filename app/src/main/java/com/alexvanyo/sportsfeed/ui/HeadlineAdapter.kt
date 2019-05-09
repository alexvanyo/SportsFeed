package com.alexvanyo.sportsfeed.ui

import androidx.recyclerview.widget.DiffUtil
import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.api.Headline
import com.alexvanyo.sportsfeed.databinding.HeadlineItemBinding
import com.alexvanyo.sportsfeed.util.DataBoundListAdapter

class HeadlineAdapter : DataBoundListAdapter<Headline, HeadlineItemBinding>(
    R.layout.headline_item,
    object : DiffUtil.ItemCallback<Headline>() {
        override fun areItemsTheSame(oldItem: Headline, newItem: Headline): Boolean =
            oldItem.description == newItem.description

        override fun areContentsTheSame(oldItem: Headline, newItem: Headline): Boolean = oldItem == newItem

    }
) {
    override fun bind(binding: HeadlineItemBinding, item: Headline) {
        binding.headline = item
    }
}