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
import com.pedrofr.sportsfinder.data.model.BetWithEvents
import com.pedrofr.sportsfinder.utils.afterTextChangedDelayed
import kotlinx.android.synthetic.main.item_pending_bet.view.*


class PendingBetsAdapter(private val onItemRemove: (Bet) -> Unit)  :
    ListAdapter<BetWithEvents, PendingBetsAdapter.ViewHolder>(PendingBetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showData(getItem(position), ::afterStakeChanged, onItemRemove)
    }

    private fun afterStakeChanged(pendingBetId: String, changedStake: String){
        //TODO element not geting returned sometimes
        val index = currentList.indexOfFirst { it.bet.betId == pendingBetId }

        currentList[index].bet.stake = changedStake.toDouble()
    }

    fun getPendingBetsWithStake(): List<Bet> {
        val bets = mutableListOf<Bet>()
        currentList.forEach {
            if(it.bet.stake != 0.0) bets.add(it.bet)
        }
        return bets
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun showData(
            pendingBet: BetWithEvents,
            afterStakeChanged: (String, String) -> Unit,
            onItemRemove: (Bet) -> Unit) {
            val totalOdd = pendingBet.bet.totalOdd
            with(itemView) {
                //TODO Change this values when the data model is finished
                eventDetails.text = context.getString(R.string.event_details, pendingBet.events.first().homeTeam, pendingBet.events.first().awayTeam) //TODO this is done wrong. With the correct data model we should be able to associate the event with the Selected team and odd
                selectedTeamName.text = pendingBet.bet.selectedTeam
                selectedOdd.text = totalOdd.toString()

                stake_edit_text.afterTextChangedDelayed {
                    if(it.isNotBlank()){
                        val amount = it.toDouble()
                        val total = amount.times(totalOdd)
                        total_edit_text.setText(String.format("%.2f", total)) //Rounds the number of decimal field to 2
                        afterStakeChanged(pendingBet.bet.betId, it)
                    }
                }

                removePendingBetBtn.setOnClickListener {
                    onItemRemove(pendingBet.bet)
                }
            }



        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_pending_bet, parent, false)
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
