package com.heixss.cats.db

import androidx.room.*

interface BaseDao<T> {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg onj: T)

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg onj: T)

    @Delete
    fun delete(obj: T)
}