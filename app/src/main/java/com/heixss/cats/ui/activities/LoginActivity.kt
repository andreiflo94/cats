package com.heixss.cats.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.heixss.cats.R
import com.heixss.cats.model.repositories.VAL_STRING_UNDEFINED
import com.heixss.cats.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModels()

    companion object {
        fun newIntent(context: Context): Intent? {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.setOnClickListener {
            val username = et_user.text.toString()
            val password = et_password.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, R.string.login_credential_errors, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.login(username, password)
        }
    }

    override fun onResume() {
        super.onResume()
        observeSession()
    }

    private fun observeSession() {
        sessionDisposable = viewModel.observeAccessToken().subscribe {
            if (it != VAL_STRING_UNDEFINED) {
                val intent = BreedsActivity.newIntent(this)
                intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}