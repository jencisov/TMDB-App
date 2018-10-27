package com.jencisov.tmdb.app.movies.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

open class BaseViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        try {
            return modelClass.newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException(e.message)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e.message)
        }

    }
}