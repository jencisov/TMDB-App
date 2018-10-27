package com.jencisov.tmdb.data.pagination.datasource._base

import android.arch.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseDataSource<T> : PageKeyedDataSource<Int, T>() {
    lateinit var onDataSourceLoading: OnDataSourceLoading

    private var compositeDisposable = CompositeDisposable()

    protected abstract fun loadInitialData(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>)
    protected abstract fun loadAdditionalData(params: LoadParams<Int>, callback: LoadCallback<Int, T>)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        if (onDataSourceLoading != null) {
            onDataSourceLoading!!.onFirstFetch()
        }
        loadInitialData(params, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        if (onDataSourceLoading != null) {
            onDataSourceLoading!!.onDataLoading()
        }
        loadAdditionalData(params, callback)
    }

    protected fun submitInitialData(items: List<T>, params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        callback.onResult(items, 0, params.requestedLoadSize)
        if (onDataSourceLoading != null) {
            if (items != null && !items.isEmpty()) {
                onDataSourceLoading!!.onFirstFetchEndWithData()
            } else {
                onDataSourceLoading!!.onFirstFetchEndWithoutData()
            }
        }
    }

    protected fun submitData(items: List<T>, params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        callback.onResult(items, if (items == null || items.isEmpty()) null else params.key + items.size)
        if (onDataSourceLoading != null) {
            onDataSourceLoading!!.onDataLoadingEnd()
        }
    }

    protected fun submitInitialError(error: Throwable) {
        onDataSourceLoading!!.onError()
        error.printStackTrace()
    }

    protected fun submitError(error: Throwable) {
        onDataSourceLoading!!.onError()
        error.printStackTrace()
    }

    override fun invalidate() {
        super.invalidate()
        compositeDisposable.dispose()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}