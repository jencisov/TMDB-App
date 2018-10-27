package com.jencisov.tmdb.data.database

import android.arch.persistence.room.Room
import android.content.Context
import android.support.annotation.MainThread
import com.jencisov.tmdb.BuildConfig

object DatabaseCreator {

    private lateinit var appDatabase: AppDatabase

    @MainThread
    fun init(context: Context) {
        appDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, BuildConfig.DATABASE_NAME
        ).build()
    }

    fun getDatabase() = appDatabase

}