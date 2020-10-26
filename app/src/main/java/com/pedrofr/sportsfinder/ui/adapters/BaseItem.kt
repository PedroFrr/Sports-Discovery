package com.pedrofr.sportsfinder.ui.adapters

/**
 * List items used in [BaseViewHolder]. Implement this with items containing data to display
 * */
abstract class BaseItem {
    abstract val layoutId: Int

    // Used to compare items when diffing so RecyclerView knows how to animate
    abstract val uniqueId: Any

    open fun bind(holder: BaseViewHolder) {
        holder.containerView.setOnClickListener {  }
    }

}