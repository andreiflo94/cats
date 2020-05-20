package com.heixss.cats.utils

import android.content.SharedPreferences
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * A wrapper on Android's [SharedPreferences]
 */
class SpDataStore(private val sp: SharedPreferences) {

    private val accessorProvider: SpAccessorProvider =
        SpAccessorProvider(sp)
    private val spSubject by lazy { PublishSubject.create<String>() }
    private val listener by lazy {
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            spSubject.onNext(
                key
            )
        }
    }

    fun contains(key: String): Boolean {
        return sp.contains(key)
    }

    fun clear() {
        if (size() == 0) {
            return
        }

        sp.edit().clear().apply()
    }

    fun remove(key: String) {
        if (!contains(key)) {
            return
        }

        sp.edit().remove(key).apply()
    }

    fun size(): Int {
        return sp.all.size
    }

    fun <T> get(key: String, classOfT: Class<T>, defaultValue: T): T {
        return if (!contains(key)) {
            defaultValue
        } else get(key,
            TypeToken.fromClass(
                classOfT
            ), defaultValue)

    }

    fun <T> get(key: String, typeTokenOfT: TypeToken, defaultValue: T): T {
        val typeOfT = typeTokenOfT.type

        for (entry in accessorProvider.getAccessors().entries) {
            if (typeOfT == entry.key) {
                val accessor = entry.value as Accessor<T>
                return accessor.get(key, defaultValue)
            }
        }

        return defaultValue
    }

    fun put(key: String, value: Any) {
        put(key, value,
            TypeToken.fromValue(value)
        )
    }

    fun <T : Any> put(key: String, value: T, typeTokenOfT: TypeToken) {
        if (!accessorProvider.getAccessors().containsKey(typeTokenOfT.type)) {
            throw Exception(typeTokenOfT.type.toString())
        }

        for (entry in accessorProvider.getAccessors().entries) {
            if (typeTokenOfT.type == entry.key) {
                val accessor = entry.value as Accessor<T>
                accessor.put(key, value)
            }
        }
    }

    fun <T> observe(key: String, classOfT: Class<T>, defaultValue: T): Observable<T> {
        return observe(key,
            TypeToken.fromClass(
                classOfT
            ), defaultValue)
    }

    fun <T> observe(key: String, typeTokenOfT: TypeToken, defaultValue: T): Observable<T> {
        return observePreferences()
            .doOnDispose {
                removePreferencesListener()
            }
            .filter { it == key }
            .map { get(it, typeTokenOfT, defaultValue) }
    }

    fun removePreferencesListener() {
        sp.unregisterOnSharedPreferenceChangeListener(listener)
    }

    /**
     * Observe shared preferences changes. Subscribe to get the changed value key
     */
    private fun observePreferences(): Observable<String> {
        sp.registerOnSharedPreferenceChangeListener(listener)
        return spSubject
    }
}
