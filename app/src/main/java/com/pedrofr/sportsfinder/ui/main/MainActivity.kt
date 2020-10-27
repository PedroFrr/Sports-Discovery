package com.pedrofr.sportsfinder.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.pedrofr.sportsfinder.R
import kotlinx.android.synthetic.main.activity_main.*

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

    private fun initUi(){
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            displayNextFragment(menuItem.itemId)
            true
        }
    }

    private fun displayNextFragment(itemId: Int) {
        when (itemId) {
            R.id.sports -> {
                displayFragment(sportsListDestination)
            }

            R.id.userAccount -> {
                displayFragment(userAccountDestination)
            }
        }
    }

    private fun displayFragment(destinationFragment: Int) {
        findNavController(R.id.nav_host_fragment).navigate(destinationFragment)
    }
}