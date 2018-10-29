package com.jencisov.tmdb.app.ui.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.jencisov.tmdb.app.utils.Event
import com.jencisov.tmdb.data.pagination.datasource._base.OnDataSourceLoading
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePaginationViewModel<T : DataSource.Factory<Int, K>, K> : ViewModel() {
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    protected lateinit var dataSourceFactory : T
    private var pagedObservable: Observable<PagedList<K>>? = null

    val clearDataEvents : MutableLiveData<Event<Unit>> get() = _clearDataEvents
    private val _clearDataEvents = MutableLiveData<Event<Unit>>()

    val emptyVisibilityEvents : MutableLiveData<Event<Boolean>> get() = _emptyVisibilityEvents
    private val _emptyVisibilityEvents = MutableLiveData<Event<Boolean>>()

    val recyclerViewLoadingEvents : MutableLiveData<Event<Boolean>> get() = _recyclerViewLoadingEvents
    private val _recyclerViewLoadingEvents = MutableLiveData<Event<Boolean>>()

    val errorEvent : MutableLiveData<Event<Unit>> get() = _errorToastEvent
    private val _errorToastEvent = MutableLiveData<Event<Unit>>()

    abstract fun getPageSize() : Int

    fun getItems(): Observable<PagedList<K>>? {
        if (pagedObservable == null) {
            createPagedObservable()
        }
        return pagedObservable
    }

    fun createPagedObservable() {
        pagedObservable = RxPagedListBuilder(
                dataSourceFactory,
                PagedList.Config.Builder()
                        .setPageSize(getPageSize())
                        .setEnablePlaceholders(false)
                        .build())
                .buildObservable()
    }

    protected fun getListener(): OnDataSourceLoading {
        return object : OnDataSourceLoading {
            override fun onFirstFetch() {
                showRecyclerLoading()
            }

            override fun onFirstFetchEndWithData() {
                hideRecyclerLoading()
                hideEmptyVisibility()
            }

            override fun onFirstFetchEndWithoutData() {
                hideRecyclerLoading()
                showEmptyVisibility()
            }

            override fun onDataLoading() {
                showRecyclerLoading()
            }

            override fun onDataLoadingEnd() {
                hideRecyclerLoading()
            }

            override fun onInitialError() {
                hideRecyclerLoading()
                showEmptyVisibility()
                showErrorToast()
            }

            override fun onError() {
                hideRecyclerLoading()
                showEmptyVisibility()
                showErrorToast()
            }
        }
    }

    fun showEmptyVisibility() {
        emptyVisibilityEvents.postValue(Event(true))
    }

    fun hideEmptyVisibility() {
        emptyVisibilityEvents.postValue(Event(false))
    }

    fun showRecyclerLoading() {
        recyclerViewLoadingEvents.postValue(Event(true))
    }

    fun hideRecyclerLoading() {
        recyclerViewLoadingEvents.postValue(Event(false))
    }

    fun showErrorToast() {
        errorEvent.postValue(Event(Unit))
    }

    fun addDisposable(d : Disposable) = compositeDisposable.add(d)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}