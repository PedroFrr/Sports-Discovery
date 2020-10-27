package com.pedrofr.sportsfinder.ui.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.ui.adapters.BaseItem
import com.pedrofr.sportsfinder.data.model.HeaderItem
import com.pedrofr.sportsfinder.data.model.Event
import com.pedrofr.sportsfinder.data.model.EventItem
import com.pedrofr.sportsfinder.networking.Failure
import com.pedrofr.sportsfinder.networking.Loading
import com.pedrofr.sportsfinder.networking.Success
import com.pedrofr.sportsfinder.utils.convertToDayMonth
import com.pedrofr.sportsfinder.viewmodels.OddsListViewModel
import kotlinx.android.synthetic.main.fragment_events_list.*
import kotlinx.android.synthetic.main.fragment_events_list.loadingProgressBar
import kotlinx.android.synthetic.main.fragment_events_list.statusButton
import kotlinx.android.synthetic.main.fragment_sports_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EventListFragment : Fragment() {

    private val eventsAdapter by lazy { OddListAdapter() }
    private val viewModel: OddsListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        updateData()
        loadEventsList()

    }

    private fun initUi() {
        eventsRecyclerView.apply {
            adapter = eventsAdapter
            hasFixedSize()
        }

    }

    private fun updateData() {
        arguments?.let {
            val args = EventListFragmentArgs.fromBundle(it)
            val sportsKey = args.sportsKey
            viewModel.fetchEvents(sportsKey)
        }
    }

    private fun loadEventsList() {
        viewModel.result.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Loading -> {
                    statusButton.visibility = View.GONE
                    eventsRecyclerView.visibility = View.GONE
                    loadingProgressBar.visibility = View.VISIBLE
                }
                is Success -> {
                    statusButton.visibility = View.GONE
                    eventsRecyclerView.visibility = View.VISIBLE
                    loadingProgressBar.visibility = View.GONE
                    val orderedOdds = createAlphabetizedOdds((result as Success<List<Event>>).data)
                    eventsAdapter.submitList(orderedOdds)
                }
                is Failure -> {
                    statusButton.visibility = View.VISIBLE
                    context?.let {
                        statusButton.setCompoundDrawables(
                            null, ContextCompat.getDrawable(it, R.drawable.no_internet), null,
                            null)
                    }
                    eventsRecyclerView.visibility = View.GONE
                    loadingProgressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun createAlphabetizedOdds(events: List<Event>): MutableList<BaseItem> {
        // Wrap data in list items
        val eventItems = events.map { EventItem(
            eventKey = it.eventId,
            startTime= it.startTime,
            homeTeam= it.homeTeam,
            awayTeam= it.awayTeam,
            homeTeamOdd= it.homeTeamOdd,
            awayTeamOdd= it.awayTeamOdd,
            drawOdd= it.drawOdd,
        ) }

        val eventsWithHeaders = mutableListOf<BaseItem>()

        // Loop through the fruit list and add headers where we need them
        var currentHeader: String? = null
        eventItems.forEach { eventItem ->
            val startDate = convertToDayMonth(eventItem.startTime)
            startDate.let {startTime ->
                if (startTime != currentHeader) {
                    eventsWithHeaders.add(HeaderItem(startTime))
                    currentHeader = startTime
                }
            }
            eventsWithHeaders.add(eventItem)
        }
        return eventsWithHeaders
    }
}
