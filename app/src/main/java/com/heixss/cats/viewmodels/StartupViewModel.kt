package com.heixss.cats.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.heixss.cats.model.repositories.SharedPreferencesRepository
import io.reactivex.Observable

class StartupViewModel @ViewModelInject constructor(private val sharedPreferencesRepository: SharedPreferencesRepository) :
    ViewModel() {

    fun observeAccessToken(): Observable<String> {
        return sharedPreferencesRepository.observeAccessToken()
    }
}
