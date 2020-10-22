package com.pedrofr.sportsfinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_sports_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SportsDetailFragment : Fragment() {

    private val repository by lazy { App.repository}

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sports_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO inner RecyclerView

        arguments?.let {
            val args = SportsDetailFragmentArgs.fromBundle(it)
            val sportsKey = args.sportsKey
            lifecycleScope.launch(Dispatchers.IO) {
                val odds = repository.getOdds(sportsKey)



            }


        }

    }
}