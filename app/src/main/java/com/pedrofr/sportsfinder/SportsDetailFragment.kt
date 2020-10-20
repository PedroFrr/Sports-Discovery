package com.pedrofr.sportsfinder

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
            val args = SportsDetailFragmentArgs.fromBundle(it)
            val sportsKey = args.sportsKey
//            val sports = App.sports
//            val sport = sports.first { sport ->
//                sport.sportsKey.toString() == sportsKey
//            }
//            sport_title.text = sport.title

        }

    }
}