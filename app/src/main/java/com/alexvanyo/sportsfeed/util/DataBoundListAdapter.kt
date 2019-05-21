package com.alexvanyo.sportsfeed.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/**
 * An abstract list adapter for use with a simple RecyclerView where each item has the same layout and is databound to
 * an item in the list.
 * @see <a href="https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/ui/common/DataBoundListAdapter.kt">based on (Apache license)</a>
 */
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