package com.pedrofr.sportsfinder.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val pendingBetsAdapter by lazy { PendingBetsAdapter(::onFocusChange) }
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
            //TODO logic to place bet
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

    private fun onFocusChange(){
            //TODO call calculator to display amount
            Toast.makeText(this, "Changed focus", Toast.LENGTH_SHORT).show()
    }

}