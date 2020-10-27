package com.pedrofr.sportsfinder.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.data.model.User
import com.pedrofr.sportsfinder.ui.sports.SportsListFragmentDirections
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_user_account.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Observer

class UserAccountFragment() : Fragment() {

    private val sharedPrefs by inject<SharedPrefManager> ()
    private val viewModel: UserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi(view)

    }

    private fun initUi(view: View) {
        historyButton.setOnClickListener {
            navigateToListBets(view)
        }

        viewModel.result.observe(viewLifecycleOwner) {user ->
            userBalance.text = user.balance.toString()
        }


    }

    private fun navigateToListBets(view: View) {

        val direction =
            UserAccountFragmentDirections.actionUserToHistory(
                sharedPrefs.getLoggedInUserId()
            )
        view.findNavController().navigate(direction)
    }

}