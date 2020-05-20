package com.heixss.cats.model.repositories

import com.heixss.cats.model.remote.Token
import javax.inject.Inject

class LoginRepository @Inject constructor() {

    //mocked login
    fun login(userName: String, password: String): Token {
        return Token("429494v239m43v2fdv532523v5325bnfsdfgnarymaxmd")
    }
}