package com.pedrofr.sportsfinder.data.model

import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.adapters.BaseItem

data class Header(val date: String) : BaseItem() {

    override val layoutId = R.layout.layout_header_item

    override val uniqueId = date
}