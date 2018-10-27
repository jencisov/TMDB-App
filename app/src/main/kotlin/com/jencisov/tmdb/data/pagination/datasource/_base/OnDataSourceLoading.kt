package com.jencisov.tmdb.data.pagination.datasource._base

interface OnDataSourceLoading {
    fun onFirstFetch()
    fun onFirstFetchEndWithData()
    fun onFirstFetchEndWithoutData()
    fun onDataLoading()
    fun onDataLoadingEnd()
    fun onInitialError()
    fun onError()
}