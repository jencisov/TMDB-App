package com.jencisov.tmdb.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jencisov.tmdb.data.database.dao.MovieDao
import com.jencisov.tmdb.data.database.entity.MovieEntity

@Database(entities = arrayOf(MovieEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}