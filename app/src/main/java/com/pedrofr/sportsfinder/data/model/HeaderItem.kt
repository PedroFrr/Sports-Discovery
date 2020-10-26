package com.pedrofr.sportsfinder.data.model

import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.ui.adapters.BaseItem
import com.pedrofr.sportsfinder.ui.adapters.BaseViewHolder
import kotlinx.android.synthetic.main.layout_header_item.view.*

data class HeaderItem(val date: String) : BaseItem() {

    override val layoutId = R.layout.layout_header_item

    override val uniqueId = date

    override fun bind(holder: BaseViewHolder) {
        super.bind(holder)
        holder.containerView.text_header.text = date
    }
}