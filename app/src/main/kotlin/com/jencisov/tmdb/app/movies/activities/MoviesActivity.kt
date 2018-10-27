package com.jencisov.tmdb.app.movies.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import com.jencisov.tmdb.R
import com.jencisov.tmdb.app.movies.adapter.MoviesAdapter
import com.jencisov.tmdb.app.movies.lifecycle.BaseLifeCycleActivity
import com.jencisov.tmdb.app.movies.util.EndlessRecyclerViewScrollListener
import com.jencisov.tmdb.app.movies.viewmodel.UpcomingMoviesViewModel
import com.jencisov.tmdb.app.movies.viewmodel.UpcomingMoviesViewModelFactory
import com.jencisov.tmdb.data.database.DatabaseCreator
import com.jencisov.tmdb.data.database.entity.MovieEntity
import com.jencisov.tmdb.data.webservice.ApiFactory
import com.jencisov.tmdb.data.webservice.response.Response

class MoviesActivity : BaseLifeCycleActivity() {

    private val mViewModel: UpcomingMoviesViewModel by lazy { getViewModel() }
    private val mMoviesAdapter: MoviesAdapter by lazy { MoviesAdapter() }

    private lateinit var upcomingMoviesRv: RecyclerView
    private lateinit var upcomingLoadingView: FrameLayout
    private lateinit var upcomingEmptyState: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        initView()
        subscribeUi()
    }

    private fun initView() {
        upcomingMoviesRv = findViewById(R.id.upcoming_movies_rv)
        upcomingLoadingView = findViewById(R.id.upcoming_movies_loader)
        upcomingEmptyState = findViewById(R.id.upcoming_movies_empty_state)

        upcomingMoviesRv.adapter = mMoviesAdapter
        upcomingMoviesRv.addOnScrollListener(
            object : EndlessRecyclerViewScrollListener(upcomingMoviesRv.layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    mViewModel.upcomingMoviesList(1).observe(this@MoviesActivity,
                        Observer<Response<MutableList<MovieEntity>>> { moviesResponse ->
                            if (moviesResponse?.data != null) {
                                mMoviesAdapter.setList(moviesResponse.data)
                                upcomingEmptyState.visibility = View.GONE
                            } else {
                                upcomingEmptyState.visibility = View.VISIBLE
                            }
                        })
                }
            })
    }

    private fun subscribeUi() {
        mViewModel.upcomingMoviesList(1).observe(this,
            Observer<Response<MutableList<MovieEntity>>> { moviesResponse ->
                if (moviesResponse?.data != null) {
                    mMoviesAdapter.setList(moviesResponse.data)
                    upcomingEmptyState.visibility = View.GONE
                } else {
                    upcomingEmptyState.visibility = View.VISIBLE
                }
            })

        mViewModel.isLoading().observe(this,
            Observer<Boolean> { isLoading ->
                if (isLoading != null && isLoading) {
                    upcomingLoadingView.visibility = View.VISIBLE
                } else {
                    upcomingLoadingView.visibility = View.GONE
                }
            })
    }

    private fun getViewModel(): UpcomingMoviesViewModel {
        val service = ApiFactory().getUpcomingMoviesService()
        val moviesDao = DatabaseCreator.getDatabase().movieDao()
        val factory = UpcomingMoviesViewModelFactory(service, moviesDao)
        return ViewModelProviders.of(this, factory).get(UpcomingMoviesViewModel::class.java)
    }

}