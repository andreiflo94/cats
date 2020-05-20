package com.heixss.cats.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.heixss.cats.R

class BreedsActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent? {
            return Intent(context, BreedsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breeds)
    }
}