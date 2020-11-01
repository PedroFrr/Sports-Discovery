package com.pedrofr.sportsfinder.ui.main

import android.annotation.SuppressLint
import android.content.res.Resources
import android.provider.Settings.System.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.data.model.Bet
import com.pedrofr.sportsfinder.data.model.BetWithEvents
import kotlinx.android.synthetic.main.list_item_pending_bet.view.*


class PendingBetsAdapter() : ListAdapter<BetWithEvents, PendingBetsAdapter.ViewHolder>(PendingBetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showData(getItem(position))
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun showData(pendingBet: BetWithEvents) {
            //TODO Change this values when the data model is finished
            itemView.eventDetails.text = itemView.context.getString(R.string.event_details, pendingBet.events.first().homeTeam, pendingBet.events.first().awayTeam) //TODO this is done wrong. With the correct data model we should be able to associate the event with the Selected team and odd
            itemView.selectedTeamName.text = pendingBet.bet.selectedTeam
            itemView.selectedOdd.text = pendingBet.bet.totalOdd.toString()

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

private class PendingBetDiffCallback : DiffUtil.ItemCallback<BetWithEvents>() {
    override fun areItemsTheSame(oldItem: BetWithEvents, newItem: BetWithEvents): Boolean {
        return oldItem.bet.betId == newItem.bet.betId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: BetWithEvents, newItem: BetWithEvents): Boolean {
        return oldItem == newItem
    }


}
