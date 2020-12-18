package com.pedrofr.sportsfinder.ui.sports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.networking.Failure
import com.pedrofr.sportsfinder.networking.Loading
import com.pedrofr.sportsfinder.networking.NoResults
import com.pedrofr.sportsfinder.networking.Success
import com.pedrofr.sportsfinder.viewmodels.SportsListViewModel
import kotlinx.android.synthetic.main.fragment_sports_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SportsListFragment : Fragment() {

    private val sportsListAdapter by lazy { SportsListAdapter() }
    private val viewModel: SportsListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sports_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservables()

    }


    private fun initUi() {

        /*KTX Core addTextChangedListener
        * Once search changes (and after debounce period) display new data
         */
        searchEditText.doOnTextChanged { text, _, _, _ ->
            val searchedTitle = text.toString()
            viewModel.fetchSportsByTitle(searchedTitle)
        }

        sportRecyclerView.apply {
            adapter = sportsListAdapter
            hasFixedSize()
        }

    }

    private fun initObservables() {
        viewModel.getResultLiveData.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Loading -> {
                    statusButton.visibility = View.GONE
                    sportRecyclerView.visibility = View.GONE
                    loadingProgressBar.visibility = View.VISIBLE
                }
                is Success -> {
                    statusButton.visibility = View.GONE
                    sportRecyclerView.visibility = View.VISIBLE
                    loadingProgressBar.visibility = View.GONE
                    sportsListAdapter.submitList(((result) as Success<List<Sport>>).data)
                }
                is Failure -> {
                    statusButton.visibility = View.VISIBLE
                    statusButton.text =  getString(R.string.no_internet)
                    context?.let {
                        statusButton.setCompoundDrawables(
                            null, ContextCompat.getDrawable(it, R.drawable.no_internet), null,
                            null)
                    }
                    sportRecyclerView.visibility = View.GONE
                    loadingProgressBar.visibility = View.GONE
                }
                is NoResults -> {
                    statusButton.visibility = View.VISIBLE
                    statusButton.text = getString(R.string.no_results)
                    context?.let {
                        statusButton.setCompoundDrawables(
                            null, ContextCompat.getDrawable(it, R.drawable.ic_outline_error_24), null,
                            null)
                    }
                    sportRecyclerView.visibility = View.GONE
                    loadingProgressBar.visibility = View.GONE
                }
            }
        })
    }

}