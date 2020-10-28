package com.pedrofr.sportsfinder.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pedrofr.sportsfinder.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.*

class MainActivity : AppCompatActivity() {

    private val sportsListDestination: Int = R.id.SportListFragment
    private val userAccountDestination: Int = R.id.userAccountFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()

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
                displayFragment(sportsListDestination)
            }

            R.id.userAccount -> {
                displayFragment(userAccountDestination)
            }
        }
        return true
    }

    private fun initUi() {
        val bottomSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)

        floatingActionButton.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }


    private fun displayFragment(destinationFragment: Int) {
        findNavController(R.id.nav_host_fragment).navigate(destinationFragment)
    }
}