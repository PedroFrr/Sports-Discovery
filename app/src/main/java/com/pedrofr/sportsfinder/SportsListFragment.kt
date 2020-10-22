package com.pedrofr.sportsfinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrofr.sportsfinder.adapters.SportsListAdapter
import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.viewmodels.SportsListViewModel

import kotlinx.android.synthetic.main.fragment_animal_list.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SportsListFragment : Fragment() {

    private val adapter by lazy { SportsListAdapter() }
    private val viewModel : SportsListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_animal_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        loadSportsList()

    }

    private fun initUi() {
        animalRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        animalRecyclerView.adapter = adapter
    }

    private fun loadSportsList(){
        lifecycleScope.launch {
            viewModel.result.observe(viewLifecycleOwner) { sports ->
                adapter.submitList(sports)
            }
        }
    }
}