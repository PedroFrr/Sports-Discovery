package com.pedrofr.sportsfinder.ui.events

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pedrofr.sportsfinder.ui.adapters.BaseItem
import com.pedrofr.sportsfinder.ui.adapters.BaseViewHolder

class OddListAdapter() : ListAdapter<BaseItem, BaseViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return BaseViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int = getItem(position).layoutId

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        getItem(position).bind(holder)
//        getItem(position).itemClickCallback = itemClickCallback -- enable to allow for click cell action
    }


}

private class EventDiffCallback : DiffUtil.ItemCallback<BaseItem>() {
    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem.uniqueId == newItem.uniqueId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem == newItem
    }


}
