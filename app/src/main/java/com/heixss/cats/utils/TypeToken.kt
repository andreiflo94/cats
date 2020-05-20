package com.heixss.cats.utils

import java.lang.reflect.Type

class TypeToken private constructor(classOfT: Type) {

    val type: Type = classOfT

    companion object {
        internal fun fromClass(classForT: Type): TypeToken {
            return TypeToken(classForT)
        }

        internal fun fromValue(value: Any): TypeToken {
            return TypeToken(value::class.java)
        }
    }
}
