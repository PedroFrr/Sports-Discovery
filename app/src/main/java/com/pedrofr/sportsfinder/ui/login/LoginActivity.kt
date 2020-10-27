package com.pedrofr.sportsfinder.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pedrofr.sportsfinder.R
import com.pedrofr.sportsfinder.ui.main.MainActivity
import com.pedrofr.sportsfinder.viewmodels.LoginViewModel
import com.pedrofr.sportsfinder.viewmodels.LoginViewState
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        checkIfUserLoggedIn()
        initObservables()
        initUi()
    }

    private fun initUi() {
        loginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()
        viewModel.loginUser(username, password)

    }

    private fun initObservables() {
        viewModel.getLoginState().observe(this, { loginViewState ->
            when (loginViewState) {
                is LoginViewState.UserLoggedIn -> navigateToMainScreen()
                is LoginViewState.InvalidCredentials -> displayErrorMessage()
            }
        })


    }

    private fun displayErrorMessage() {
        Toast.makeText(
            this,
            getString(R.string.invalid_credentials),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun checkIfUserLoggedIn() {
        viewModel.checkIfUserIsLoggedIn()
    }

}
