package com.jencisov.tmdb.app.movies.lifecycle

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.support.annotation.MainThread
import android.support.v7.app.AppCompatActivity

open class BaseLifeCycleActivity : AppCompatActivity(), LifecycleOwner {

    private val lifecycle = LifecycleRegistry(this)

    @MainThread
    override fun getLifecycle(): LifecycleRegistry {
        return lifecycle
    }

}