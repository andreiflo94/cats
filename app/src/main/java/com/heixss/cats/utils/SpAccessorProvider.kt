package com.heixss.cats.utils

import android.content.SharedPreferences
import java.util.*

class SpAccessorProvider internal constructor(private val sp: SharedPreferences) :
    AccessorProvider {

    private val accessors: MutableMap<Class<*>, Accessor<*>> = HashMap()

    init {
        initAccessors()
    }

    override fun getAccessors(): Map<Class<*>, Accessor<*>> {
        return accessors
    }

    private fun initAccessors() {
        initBooleanAccessor()
        initStringAccessor()
        initIntegerAccessor()
        initLongAccessor()
    }

    private fun initBooleanAccessor() {
        accessors[Boolean::class.javaObjectType] = object :
            Accessor<Boolean> {
            override fun get(key: String, defaultValue: Boolean): Boolean {
                return sp.getBoolean(key, defaultValue)
            }

            override fun put(key: String, value: Boolean) {
                sp.edit().putBoolean(key, value).apply()
            }
        }
    }

    private fun initStringAccessor() {
        accessors[String::class.javaObjectType] = object :
            Accessor<String> {
            override fun get(key: String, defaultValue: String): String {
                return sp.getString(key, defaultValue).toString()
            }

            override fun put(key: String, value: String) {
                sp.edit().putString(key, value).apply()
            }
        }
    }

    private fun initIntegerAccessor() {
        accessors[Int::class.javaObjectType] = object :
            Accessor<Int> {
            override fun get(key: String, defaultValue: Int): Int {
                return sp.getInt(key, defaultValue)
            }

            override fun put(key: String, value: Int) {
                sp.edit().putInt(key, value).apply()
            }
        }
    }

    private fun initLongAccessor() {
        accessors[Long::class.javaObjectType] = object :
            Accessor<Long> {
            override fun get(key: String, defaultValue: Long): Long {
                return sp.getLong(key, defaultValue)
            }

            override fun put(key: String, value: Long) {
                sp.edit().putLong(key, value).apply()
            }
        }
    }
}
