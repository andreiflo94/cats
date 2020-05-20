package com.heixss.cats.viewmodels

import androidx.lifecycle.ViewModel
import com.heixss.cats.model.repositories.SharedPreferencesRepository
import io.reactivex.Observable
import javax.inject.Inject

class StartupViewModel @Inject constructor(private val sharedPreferencesRepository: SharedPreferencesRepository) :
    ViewModel() {

    fun observeAccessToken(): Observable<String> {
        return sharedPreferencesRepository.observeAccessToken()
    }
}
