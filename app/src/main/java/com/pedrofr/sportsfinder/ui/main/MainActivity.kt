package com.pedrofr.sportsfinder.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.data.model.Bet
import com.pedrofr.sportsfinder.utils.toast
import com.pedrofr.sportsfinder.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val pendingBetsAdapter by lazy { PendingBetsAdapter(::onItemRemove) }
    private val viewModel: MainActivityViewModel by viewModel()

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

    private fun initUi() {

        val bottomSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })

        floatingActionButton.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        pendingBetsRecyclerView.apply {
            adapter = pendingBetsAdapter
            hasFixedSize()
        }

        betButton.setOnClickListener {
            onBetButtonClick()
        }

        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sports -> {
                    displayFragment(R.id.SportListFragment)
                    true
                }

                R.id.userAccount -> {
                    displayFragment(R.id.userAccountFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun displayFragment(destinationFragment: Int) {
        findNavController(R.id.nav_host_fragment).navigate(destinationFragment)
    }

    private fun initObservables() {
        lifecycleScope.launch {
            viewModel.result.observe(this@MainActivity, {
                    pendingBetsAdapter.submitList(it)
            })
        }

        viewModel.getSaveLiveData().observe(this, {
            toast("Bets done!")
        })
    }

    private fun onBetButtonClick(){
        val bets = pendingBetsAdapter.getPendingBetsWithStake()
        viewModel.saveBet(bets)

    }

    private fun onItemRemove(pendingBet: Bet){
        viewModel.removePendingBet(pendingBet)
    }

}