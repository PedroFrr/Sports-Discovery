package com.pedrofr.sportsfinder.ui.sports


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.databinding.ListItemSportBinding

class SportsListAdapter: ListAdapter<Sport, RecyclerView.ViewHolder>(SportDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SportViewHolder(
            ListItemSportBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sport = getItem(position)
        (holder as SportViewHolder).bind(sport)
    }

    class SportViewHolder(
        private val binding: ListItemSportBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.sport?.let { sport ->
                    navigateToEventList(sport, it)
                }
            }
        }

        private fun navigateToEventList(
            sport: Sport,
            view: View
        ) {
            val direction =
                SportsListFragmentDirections.actionListToDetail(
                    sport.sports_key
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: Sport) {
            binding.apply {
                sport = item
                executePendingBindings()
            }
        }
    }

}

private class SportDiffCallback : DiffUtil.ItemCallback<Sport>() {

    override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean {
        return oldItem.sportId == newItem.sportId
    }

    override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean {
        return oldItem == newItem
    }
}
