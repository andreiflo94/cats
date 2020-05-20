package com.heixss.cats.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.heixss.cats.viewmodels.ViewModelFactory
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var sessionDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        if (::sessionDisposable.isInitialized)
            sessionDisposable.dispose()
        super.onPause()
    }
}