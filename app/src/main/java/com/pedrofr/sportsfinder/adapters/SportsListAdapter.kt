package com.pedrofr.sportsfinder.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.sportsfinder.SportsListFragmentDirections

import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.databinding.ListItemAnimalBinding

class AnimalListAdapter: ListAdapter<Sport, RecyclerView.ViewHolder>(AnimalDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AnimalViewHolder(
            ListItemAnimalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val animal = getItem(position)
        (holder as AnimalViewHolder).bind(animal)
    }

    class AnimalViewHolder(
        private val binding: ListItemAnimalBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.sport?.let { sport ->
                    navigateToOdds(sport, it)
                }
            }
        }

        private fun navigateToOdds(
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

private class AnimalDiffCallback : DiffUtil.ItemCallback<Sport>() {

    override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean {
        return oldItem == newItem
    }
}
