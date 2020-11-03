package com.pedrofr.sportsfinder.ui.history

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.data.model.Bet
import com.pedrofr.sportsfinder.data.model.BetWithEvents
import com.pedrofr.sportsfinder.utils.gone
import com.pedrofr.sportsfinder.utils.visible
import kotlinx.android.synthetic.main.item_history_bet.view.*


class BetHistoryListAdapter(private val onClickListener: (view: View, bet: Bet) -> Unit)  :
    ListAdapter<BetWithEvents, BetHistoryListAdapter.ViewHolder>(BetWithEventsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showData(getItem(position), onClickListener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun showData(betWithEvents: BetWithEvents, onClickListener: (view: View, bet: Bet) -> Unit) {
            with(itemView) {
                //TODO Change this values when the data model is finished
                historyEventDetails.text = context.getString(R.string.event_details, betWithEvents.events.first().homeTeam, betWithEvents.events.first().awayTeam) //TODO this is done wrong. With the correct data model we should be able to associate the event with the Selected team and odd
                historySelectedTeamName.text = betWithEvents.bet.selectedTeam
                val stake = String.format("%.2f", betWithEvents.bet.stake)
                val totalOdd =  String.format("%.2f", betWithEvents.bet.totalOdd)
                stakeAndOdd.text = context.getString(R.string.stake_odd, stake, totalOdd)

                if(betWithEvents.bet.isSettled){
                    nonSettledLayout.gone()
                    settledLayout.visible()
                    if(betWithEvents.bet.isWon){
                        wonBetIcon.visible()
                    }else{
                        lostBetIcon.visible()
                    }
                }

                markAsWonBtn.setOnClickListener {

                    onClickListener(it, betWithEvents.bet)
                }

                markAsLostBtn.setOnClickListener {
                    onClickListener(it, betWithEvents.bet)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_history_bet, parent, false)
                return ViewHolder(view)
            }
        }
    }

}

private class BetWithEventsDiffCallback : DiffUtil.ItemCallback<BetWithEvents>() {
    override fun areItemsTheSame(oldItem: BetWithEvents, newItem: BetWithEvents): Boolean {
        return oldItem.bet.betId == newItem.bet.betId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: BetWithEvents, newItem: BetWithEvents): Boolean {
        return oldItem == newItem
    }


}
