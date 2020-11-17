package com.heixss.cats.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.heixss.cats.R
import com.heixss.cats.model.repositories.VAL_STRING_UNDEFINED
import com.heixss.cats.viewmodels.StartupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_startup.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class StartupActivity : BaseActivity() {

    private val viewModel: StartupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)
        Glide.with(this).load(resources.getIdentifier("bg_startup", "drawable", packageName))
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(iv_bg)
    }

    override fun onResume() {
        super.onResume()
        observeSession()
    }

    private fun observeSession() {
        sessionDisposable =
            viewModel.observeAccessToken().delay(3000, TimeUnit.MILLISECONDS).subscribe {
                if (it == VAL_STRING_UNDEFINED) {
                    val intent = LoginActivity.newIntent(this)
                    intent!!.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    val intent = BreedsActivity.newIntent(this)
                    intent!!.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
    }
}
