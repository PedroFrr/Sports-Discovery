package com.pedrofr.sportsfinder.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.data.model.Event
import com.pedrofr.sportsfinder.networking.Failure
import com.pedrofr.sportsfinder.networking.Loading
import com.pedrofr.sportsfinder.networking.Success
import com.pedrofr.sportsfinder.utils.convertToDayMonth
import com.pedrofr.sportsfinder.viewmodels.EventsListViewModel
import kotlinx.android.synthetic.main.fragment_events_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EventListFragment : Fragment() {

    private val eventsAdapter by lazy { EventListAdapter(::onClickListener) }
    private val viewModel: EventsListViewModel by viewModel()

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

    private fun createAlphabetizedOdds(events: List<Event>): MutableList<DataItem> {

        val eventsWithHeaders = mutableListOf<DataItem>()

        // Loop through the fruit list and add headers where we need them
        var currentHeader: String? = null
        events.forEach { eventItem ->
            val startDate = convertToDayMonth(eventItem.startTime)
            startDate.let {startTime ->
                if (startTime != currentHeader) {
                    eventsWithHeaders.add(DataItem.Header(startTime))
                    currentHeader = startTime
                }
            }
            eventsWithHeaders.add(DataItem.EventItem(eventItem))
        }
        return eventsWithHeaders
    }

    private fun onHomeTeamSelection() {
        Toast.makeText(activity, "Home Team Selected", Toast.LENGTH_SHORT).show()
    }

    private fun onAwayTeamSelection() {
        //TODO Handle home team selection -> Create new Bet with this team and odd...
    }

    private fun onDrawSelection() {
        Toast.makeText(activity, "Home Team Selected", Toast.LENGTH_SHORT).show()
    }

    private fun onClickListener( v: View){
        when (v.id) {
            R.id.homeTeamOddBtn -> {
                Toast.makeText(activity, "Home Team Selected", Toast.LENGTH_SHORT).show()
            }
            R.id.awayTeamOddBtn -> {
                Toast.makeText(activity, "AwayTeam Selected", Toast.LENGTH_SHORT).show()
            }
            R.id.drawOddBtn -> {
                Toast.makeText(activity, "Draw Team Selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
