package com.pedrofr.sportsfinder.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.data.model.BetWithEvents
import com.pedrofr.sportsfinder.networking.Failure
import com.pedrofr.sportsfinder.networking.Loading
import com.pedrofr.sportsfinder.networking.Success
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager
import com.pedrofr.sportsfinder.viewmodels.EventHistoryViewModel
import kotlinx.android.synthetic.main.fragment_event_history.*
import kotlinx.android.synthetic.main.fragment_events_list.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventHistoryFragment : Fragment() {

    private val eventsAdapter by lazy { BetHistoryListAdapter() }
    private val sharedPrefs by inject<SharedPrefManager>()
    private val viewModel: EventHistoryViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        updateData()
        initObservables()
    }

    private fun initUi() {
        nonPendingBetsRecyclerView.apply {
            adapter = eventsAdapter
            hasFixedSize()
        }
    }

    private fun updateData() {
        val userId = sharedPrefs.getLoggedInUserId()
        viewModel.fetchBets(userId)
    }

    private fun initObservables(){
        viewModel.result.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Loading -> {
                    eventsHistoryStatusBtn.visibility = View.GONE
                    nonPendingBetsRecyclerView.visibility = View.GONE
                    historyLoadingProgressBar.visibility = View.VISIBLE
                }
                is Success -> {
                    eventsHistoryStatusBtn.visibility = View.GONE
                    nonPendingBetsRecyclerView.visibility = View.VISIBLE
                    historyLoadingProgressBar.visibility = View.GONE
                    lifecycleScope.launch {
                        val bets = (result as Success<List<BetWithEvents>>).data
                        eventsAdapter.submitList(bets)
                    }

                }
                is Failure -> {
                    eventsHistoryStatusBtn.visibility = View.VISIBLE
                    context?.let {
                        statusButton.setCompoundDrawables(
                            null, ContextCompat.getDrawable(it, R.drawable.no_internet), null,
                            null)
                    }
                    nonPendingBetsRecyclerView.visibility = View.GONE
                    historyLoadingProgressBar.visibility = View.GONE
                }
            }
        })
    }

}