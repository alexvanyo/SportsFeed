package com.alexvanyo.sportsfeed.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class DataBoundListAdapter<T, B : ViewDataBinding>(
    @LayoutRes val itemLayout: Int,
    itemCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBoundViewHolder<B>>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<B> {
        return DataBoundViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                itemLayout,
                parent,
                false
            )
        )
    }

    abstract fun bind(binding: B, item: T)

    override fun onBindViewHolder(holder: DataBoundViewHolder<B>, position: Int) {
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }
}