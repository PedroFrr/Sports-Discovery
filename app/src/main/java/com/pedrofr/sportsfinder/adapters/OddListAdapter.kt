package com.pedrofr.sportsfinder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.sportsfinder.SportsListFragmentDirections
import com.pedrofr.sportsfinder.data.model.Odd
import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.databinding.ListItemOddBinding

class OddListAdapter: ListAdapter<Odd, RecyclerView.ViewHolder>(OddDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OddViewHolder(
            ListItemOddBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val odd = getItem(position)
        (holder as OddViewHolder).bind(odd)
    }


    class OddViewHolder(
        private val binding: ListItemOddBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
//            binding.setClickListener {
//                binding.sport?.let { sport ->
//                    navigateToOdds(sport, it)
//                }
//            }
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

        fun bind(item: Odd) {
            binding.apply {
                odd = item
                executePendingBindings()
            }
        }
    }

}

private class OddDiffCallback : DiffUtil.ItemCallback<Odd>() {

    override fun areItemsTheSame(oldItem: Odd, newItem: Odd): Boolean {
        return oldItem.oddsKey == newItem.oddsKey
    }

    override fun areContentsTheSame(oldItem: Odd, newItem: Odd): Boolean {
        return oldItem == newItem
    }
}
