package com.heixss.cats.utils

interface Accessor<T> {

    fun get(key: String, defaultValue: T): T

    fun put(key: String, value: T)
}
