package com.pedrofr.sportsfinder.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.data.model.Bet
import kotlinx.android.synthetic.main.list_item_pending_bet.view.*


class PendingBetsAdapter() : ListAdapter<Bet, PendingBetsAdapter.ViewHolder>(PendingBetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showData(getItem(position))
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun showData(pendingBet: Bet) {
            //TODO replace this with everything correctly
            itemView.selectionTotalOdds.text = pendingBet.totalOdd.toString()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item_pending_bet, parent, false)
                return ViewHolder(view)
            }

        }
    }

}

private class PendingBetDiffCallback : DiffUtil.ItemCallback<Bet>() {
    override fun areItemsTheSame(oldItem: Bet, newItem: Bet): Boolean {
        return oldItem.betId == newItem.betId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Bet, newItem: Bet): Boolean {
        return oldItem == newItem
    }


}
