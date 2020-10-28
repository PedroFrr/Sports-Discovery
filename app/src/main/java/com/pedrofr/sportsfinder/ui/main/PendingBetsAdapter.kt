package com.pedrofr.sportsfinder.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.sportsfinder.data.model.Bet
import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.databinding.ListItemSportBinding
import com.pedrofr.sportsfinder.ui.adapters.BaseItem
import com.pedrofr.sportsfinder.ui.adapters.BaseViewHolder
import com.pedrofr.sportsfinder.ui.sports.SportsListFragmentDirections

class PendingBetsAdapter() : ListAdapter<BaseItem, BaseViewHolder>(PendingBetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return BaseViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        getItem(position).bind(holder)
    }

}

private class PendingBetDiffCallback : DiffUtil.ItemCallback<BaseItem>() {
    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem.uniqueId == newItem.uniqueId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem == newItem
    }


}
