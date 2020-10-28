package com.pedrofr.sportsfinder.ui.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.data.model.Event
import com.pedrofr.sportsfinder.databinding.ListHeaderItemBinding
import com.pedrofr.sportsfinder.databinding.ListItemEventBinding
import kotlinx.android.synthetic.main.list_header_item.view.*

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class EventListAdapter() : ListAdapter<DataItem, RecyclerView.ViewHolder>(EventDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.EventItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val eventItem = getItem(position) as DataItem.EventItem
                holder.bind(eventItem.event)
            }
            is TextViewHolder -> {
                val headerItem = getItem(position) as DataItem.Header
                holder.bind(headerItem.date)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

//    fun addHeaderAndSubmitList(list: List<Event>?) {
//        val items = when (list) {
//            null -> listOf(DataItem.Header)
//            else -> listOf(DataItem.Header) + list.map { DataItem.EventItem(it) }
//        }
//        submitList(items)
//    }

    class TextViewHolder(view: View, private val binding: ListHeaderItemBinding): RecyclerView.ViewHolder(view) {

        fun bind(date: String) {
            itemView.text_header.text = date
        }

        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_header_item, parent, false)
                val binding = ListHeaderItemBinding.inflate(layoutInflater, parent, false)
                return TextViewHolder(view, binding)
            }

        }
    }


    class ViewHolder private constructor(private val binding: ListItemEventBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Event) {
            binding.event = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemEventBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

private class EventDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {
    abstract val id: String

    data class EventItem(val event: Event): DataItem(){
        override val id = event.eventId
    }
    data class Header(val date: String): DataItem() {
        override val id = Long.MIN_VALUE.toString()
    }

}
