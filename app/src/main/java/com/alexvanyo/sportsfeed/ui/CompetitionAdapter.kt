package com.alexvanyo.sportsfeed.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.databinding.CompetitionItemBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.competition_item.view.*

class CompetitionAdapter(val fragment: Fragment, val clickListener: (String) -> Unit) :
    RecyclerView.Adapter<CompetitionAdapter.DataBoundViewHolder<CompetitionItemBinding>>() {

    val sortedList: SortedList<Competition>

    init {
        sortedList = SortedList(Competition::class.java, object : SortedListAdapterCallback<Competition>(this) {
            override fun compare(o1: Competition, o2: Competition): Int = o1.compareTo(o2)
            override fun areContentsTheSame(oldItem: Competition, newItem: Competition): Boolean = oldItem == newItem
            override fun areItemsTheSame(item1: Competition, item2: Competition): Boolean = item1.uid == item2.uid
        })
    }

    override fun getItemCount() = sortedList.size()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<CompetitionItemBinding> {
        return DataBoundViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.competition_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<CompetitionItemBinding>, position: Int) {
        holder.binding.competition = sortedList.get(position)
        holder.itemView.setOnClickListener{ clickListener(sortedList.get(position).uid) }
        Glide.with(fragment).load(sortedList.get(position).competitors[0].team.logo)
            .into(holder.itemView.leftLogo)
        Glide.with(fragment).load(sortedList.get(position).competitors[1].team.logo)
            .into(holder.itemView.rightLogo)
        holder.binding.executePendingBindings()
    }

    class DataBoundViewHolder<out T : ViewDataBinding> constructor(val binding: T) :
        RecyclerView.ViewHolder(binding.root)
}