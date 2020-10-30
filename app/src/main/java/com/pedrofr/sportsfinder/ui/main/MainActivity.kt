package com.pedrofr.sportsfinder.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.data.model.Bet
import com.pedrofr.sportsfinder.data.model.Event
import com.pedrofr.sportsfinder.networking.Failure
import com.pedrofr.sportsfinder.networking.Loading
import com.pedrofr.sportsfinder.networking.Success
import com.pedrofr.sportsfinder.ui.adapters.BaseItem
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager
import com.pedrofr.sportsfinder.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private val pendingBetsAdapter by lazy { PendingBetsAdapter() }
    private val viewModel: MainActivityViewModel by viewModel()
    private val sharedPrefs by inject<SharedPrefManager> ()
    private val userId = sharedPrefs.getLoggedInUserId()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
        initObservables()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Defines what to do on each menu option. For now only Login is defined
        when (item.itemId) {
            R.id.sports -> {
                displayFragment(R.id.SportListFragment)
            }

            R.id.userAccount -> {
                displayFragment(R.id.userAccountFragment)
            }
        }
        return true
    }

    private fun initUi() {

        val bottomSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)


        floatingActionButton.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//                viewModel.fetchPendingBets(userId)
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        pendingBetsRecyclerView.apply {
            adapter = pendingBetsAdapter
            hasFixedSize()
        }



    }


    private fun displayFragment(destinationFragment: Int) {
        findNavController(R.id.nav_host_fragment).navigate(destinationFragment)
    }

    private fun initObservables() {
        viewModel.result.observe(this, Observer {
            pendingBetsAdapter.submitList(it)
        })
    }

}