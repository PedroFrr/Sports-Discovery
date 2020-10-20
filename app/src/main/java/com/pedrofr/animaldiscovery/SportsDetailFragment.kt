package com.pedrofr.animaldiscovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_animal_detail.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SportsDetailFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_animal_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val args = AnimalDetailFragmentArgs.fromBundle(it)
            val animalId = args.animalId
            val animals = App.sports
            val animal = animals.first { animal ->
                animal.animalId.toString() == animalId
            }
            animalName.text = animal.name

        }

    }
}