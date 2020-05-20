package com.heixss.cats.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.heixss.cats.model.local.BreedCard

/**
 * This app doesn't use migrations.
 * If you need to change db structure just increment the db version and make sure fallbackToDestructiveMigration()
 */
@Database(entities = [BreedCard::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase()  {
    abstract fun breedCardDao(): BreedCardDao
}