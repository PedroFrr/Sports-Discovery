package com.pedrofr.sportsfinder.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.data.model.Bet
import com.pedrofr.sportsfinder.data.model.BetWithEvents
import com.pedrofr.sportsfinder.utils.gone
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager
import com.pedrofr.sportsfinder.utils.toast
import com.pedrofr.sportsfinder.utils.visible
import com.pedrofr.sportsfinder.viewmodels.EventHistoryViewModel
import kotlinx.android.synthetic.main.fragment_event_history.*
import kotlinx.android.synthetic.main.item_history_bet.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventHistoryFragment : Fragment() {

    private val eventsAdapter by lazy { BetHistoryListAdapter(::onClickListener) }
    private val sharedPrefs by inject<SharedPrefManager>()
    private val viewModel: EventHistoryViewModel by viewModel()
    private val userId = sharedPrefs.getLoggedInUserId()


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
        initObservables()
    }

    private fun initUi() {
        nonPendingBetsRecyclerView.apply {
            adapter = eventsAdapter
            hasFixedSize()
        }
    }

    private fun initObservables() {
        //Init Event History items observing
        //TODO wrap in a result
        viewModel.result.observe(viewLifecycleOwner, { result ->
            eventsHistoryStatusBtn.visibility = View.GONE
            nonPendingBetsRecyclerView.visibility = View.VISIBLE
            historyLoadingProgressBar.visibility = View.GONE
            lifecycleScope.launch {
                val bets = (result as List<BetWithEvents>)
                eventsAdapter.submitList(bets)
            }

        })

        //Init settled Bet action
        viewModel.getSaveLiveData().observe(viewLifecycleOwner, { saved ->
            if (saved) {
                activity?.toast("Bet settled")
            } else {
                activity?.toast("The event hasn't been played yet")
            }
        })
    }

    private fun onClickListener(v: View, betWithEvents: BetWithEvents) {
        when (v.id) {
            R.id.markAsWonBtn -> {
                viewModel.settleBet(betWithEvents, true)
            }
            R.id.markAsLostBtn -> {
                viewModel.settleBet(betWithEvents, false)
            }

        }
    }

}