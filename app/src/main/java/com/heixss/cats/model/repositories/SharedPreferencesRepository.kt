package com.heixss.cats.model.repositories

import com.heixss.cats.utils.SpDataStore
import io.reactivex.Observable
import javax.inject.Inject

const val KEY_ACCESS_TOKEN = "token"
const val VAL_STRING_UNDEFINED = "-1"

class SharedPreferencesRepository @Inject constructor(private val spDataStore: SpDataStore) {

    /**
     * Session
     */
    fun setAccessToken(token: String) {
        spDataStore.put(KEY_ACCESS_TOKEN, token)
    }

    fun observeAccessToken(): Observable<String> {
        return spDataStore.observe(
            KEY_ACCESS_TOKEN,
            String::class.javaObjectType,
            VAL_STRING_UNDEFINED
        )
            .mergeWith(Observable.just(getAccessToken()))
    }

    private fun getAccessToken(): String {
        return spDataStore.get(KEY_ACCESS_TOKEN, String::class.javaObjectType, VAL_STRING_UNDEFINED)
    }
}