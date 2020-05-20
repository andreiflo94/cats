package com.heixss.cats.utils

import com.heixss.cats.utils.Accessor

interface AccessorProvider {

    fun getAccessors(): Map<Class<*>, Accessor<*>>
}
