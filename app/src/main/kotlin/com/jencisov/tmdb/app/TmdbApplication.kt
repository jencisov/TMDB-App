package com.jencisov.tmdb.app

import android.app.Application
import com.jencisov.tmdb.data.database.DatabaseCreator

open class TmdbApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseCreator.init(this)
    }

}