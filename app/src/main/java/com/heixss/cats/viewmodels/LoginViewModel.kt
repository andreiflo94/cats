package com.heixss.cats.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heixss.cats.model.repositories.LoginRepository
import com.heixss.cats.model.repositories.SharedPreferencesRepository
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    fun login(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = loginRepository.login(userName, password)
            sharedPreferencesRepository.setAccessToken(token.token)
        }
    }

    fun observeAccessToken(): Observable<String> {
        return sharedPreferencesRepository.observeAccessToken()
    }
}
