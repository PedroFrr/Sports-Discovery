package com.pedrofr.sportsfinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrofr.sportsfinder.adapters.OddListAdapter
import com.pedrofr.sportsfinder.data.model.Odd
import com.pedrofr.sportsfinder.networking.Failure
import com.pedrofr.sportsfinder.networking.Loading
import com.pedrofr.sportsfinder.networking.Success
import com.pedrofr.sportsfinder.viewmodels.OddsListViewModel
import kotlinx.android.synthetic.main.fragment_sports_detail.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OddListFragment : Fragment() {

    private val adapter by lazy { OddListAdapter() }
    private val viewModel: OddsListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sports_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        updateData()
        loadOddsList()

    }

    private fun initUi() {
        oddsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        oddsRecyclerView.adapter = adapter

    }

    private fun updateData() {
        arguments?.let {
            val args = OddListFragmentArgs.fromBundle(it)
            val sportsKey = args.sportsKey
            viewModel.fetchOdds(sportsKey)
        }
    }

    private fun loadOddsList() {
        viewModel.result.observe(viewLifecycleOwner, { result ->
            when (result){
                is Success -> {
                    adapter.submitList(((result) as Success<List<Odd>>).data)
                }
                is Loading -> {
                    //TODO handle loading
                }
                is Failure -> {
                    //TODO handle failure
                }
            }
        })
    }

    private fun showLoadingStatus(){

    }
}