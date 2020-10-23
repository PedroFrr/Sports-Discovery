package com.pedrofr.sportsfinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrofr.sportsfinder.ui.adapters.BaseItem
import com.pedrofr.sportsfinder.ui.adapters.OddListAdapter
import com.pedrofr.sportsfinder.data.model.HeaderItem
import com.pedrofr.sportsfinder.data.model.Event
import com.pedrofr.sportsfinder.data.model.EventItem
import com.pedrofr.sportsfinder.networking.Failure
import com.pedrofr.sportsfinder.networking.Loading
import com.pedrofr.sportsfinder.networking.Success
import com.pedrofr.sportsfinder.utils.convertToDayMonth
import com.pedrofr.sportsfinder.viewmodels.OddsListViewModel
import kotlinx.android.synthetic.main.fragment_sports_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EventListFragment : Fragment() {

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
            when (result) {
                is Success -> {
                    val orderedOdds = createAlphabetizedOdds((result as Success<List<Event>>).data)
                    adapter.submitList(orderedOdds)
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

    private fun showLoadingStatus() {

    }

    private fun createAlphabetizedOdds(events: List<Event>): MutableList<BaseItem> {
        // Wrap data in list items
        val oddItems = events.map { EventItem(
            oddsKey = it.oddsKey,
            startTime= it.startTime,
            homeTeam= it.homeTeam,
            awayTeam= it.awayTeam,
            homeTeamOdd= it.homeTeamOdd,
            awayTeamOdd= it.awayTeamOdd,
            drawOdd= it.drawOdd,
        ) }

        val oddsWithAlphabetHeaders = mutableListOf<BaseItem>()

        // Loop through the fruit list and add headers where we need them
        var currentHeader: String? = null
        oddItems.forEach { oddItem ->
            val startDate = convertToDayMonth(oddItem.startTime)
            startDate.let {startTime ->
                if (startTime != currentHeader) {
                    oddsWithAlphabetHeaders.add(HeaderItem(startTime))
                    currentHeader = startTime
                }
            }
            oddsWithAlphabetHeaders.add(oddItem)
        }
        return oddsWithAlphabetHeaders
    }
}
