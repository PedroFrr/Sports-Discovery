package com.pedrofr.animaldiscovery.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.animaldiscovery.AnimalListFragmentDirections
import com.pedrofr.animaldiscovery.data.model.Sport
import com.pedrofr.animaldiscovery.databinding.ListItemAnimalBinding

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
                binding.animal?.let { plant ->
                    navigateToAnimal(plant, it)
                }
            }
        }

        private fun navigateToAnimal(
            sport: Sport,
            view: View
        ) {
            val direction =
                AnimalListFragmentDirections.actionListToDetail(
                    sport.sportsKey.toString()
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: Sport) {
            binding.apply {
                animal = item
                executePendingBindings()
            }
        }
    }

}

private class AnimalDiffCallback : DiffUtil.ItemCallback<Sport>() {

    override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean {
        return oldItem.sportsKey == newItem.sportsKey
    }

    override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean {
        return oldItem == newItem
    }
}
