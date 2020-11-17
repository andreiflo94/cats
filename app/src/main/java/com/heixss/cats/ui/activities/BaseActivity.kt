package com.heixss.cats.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {
    lateinit var sessionDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        if (::sessionDisposable.isInitialized)
            sessionDisposable.dispose()
        super.onPause()
    }
}