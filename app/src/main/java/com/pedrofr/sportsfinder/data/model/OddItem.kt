package com.pedrofr.sportsfinder.data.model

import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.adapters.BaseItem
import com.pedrofr.sportsfinder.adapters.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_odd.view.*

class OddItem(
    val oddsKey: Long = 0,
    val startTime: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamOdd: Double = 0.0,
    val awayTeamOdd: Double = 0.0,
    val drawOdd: Double? = 0.0
) : BaseItem() {

    override val layoutId = R.layout.list_item_odd

    override val uniqueId = oddsKey

    override fun bind(holder: BaseViewHolder) {
        super.bind(holder)
        holder.containerView.eventStartTime.text = startTime
        holder.containerView.homeTeamName.text = homeTeam
        holder.containerView.awayTeamName.text = awayTeam
        holder.containerView.homeTeamOdd.text = homeTeamOdd.toString()
        holder.containerView.awayTeamOdd.text = awayTeamOdd.toString()
        holder.containerView.drawOdd.text = drawOdd.toString()

    }

}