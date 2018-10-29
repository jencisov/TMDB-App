package com.jencisov.tmdb.app.viewmodel

import android.view.View
import com.jencisov.tmdb.app.ui.activity.MoviesActivity
import com.jencisov.tmdb.app.ui.viewmodel.MovieViewModel
import com.jencisov.tmdb.data.pagination.datasource.UpcomingMoviesDataSource
import com.jencisov.tmdb.data.pagination.factory.UpcomingMoviesDataSourceFactory
import com.jencisov.tmdb.domain.models.Movie
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.android.synthetic.main.activity_movies.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class MovieViewModelTest {

    @Mock
    private lateinit var upcomingMoviesDataSourceFactory: UpcomingMoviesDataSourceFactory

    @Mock
    private lateinit var upcomingMoviesDataSource: UpcomingMoviesDataSource
    @Mock
    private lateinit var viewModel: MovieViewModel
    @Mock
    private lateinit var moviesActivity: MoviesActivity

    private val immediate = object : Scheduler() {
        override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit) = super.scheduleDirect(run, 0, unit)
        override fun createWorker() = ExecutorScheduler.ExecutorWorker(Executor { it.run() })
    }

    private val movies = listOf(
        Movie(
            335983L,
            "/2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg",
            "Venom",
            "2018-10-03",
            "Venom is a venomous venom",
            listOf(1, 2)
        ),
        Movie(
            51L,
            "/bXs0zkv2iGVViZEy78teg2ycDBm.jpg",
            "Let the right one in",
            "2018-10-10",
            "Let the right one in. Let the wrong ones go",
            listOf(3, 4)
        )
    )

    @Before
    fun setupHomeViewModel() {
        MockitoAnnotations.initMocks(this)
        viewModel = MovieViewModel()
        viewModel.createPagedObservable()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
    }

    @Test
    fun performFirstLoad() {
        upcomingMoviesDataSourceFactory.create()
        whenever(upcomingMoviesDataSource.manager.getListOfUpcomingMovies(1)).thenReturn(Single.just(movies))
        viewModel.performInitialLoad()
        verify(moviesActivity).submitItems()
    }

    @Test
    fun performNextPageLoad() {
        upcomingMoviesDataSourceFactory.create()
        whenever(upcomingMoviesDataSource.manager.getListOfUpcomingMovies(2)).thenReturn(Single.just(movies))
        viewModel.performInitialLoad()
        verify(moviesActivity).submitItems()
    }

    @Test
    fun onErrorFirstLoad() {
        upcomingMoviesDataSourceFactory.create()
        whenever(upcomingMoviesDataSource.manager.getListOfUpcomingMovies(1)).thenReturn(Single.error(Exception()))
        viewModel.performInitialLoad()
        verify(moviesActivity).upcoming_movies_empty_state.visibility == View.VISIBLE
    }

    @Test
    fun onErrorNextPageLoad() {
        upcomingMoviesDataSourceFactory.create()
        whenever(upcomingMoviesDataSource.manager.getListOfUpcomingMovies(2)).thenReturn(Single.error(Exception()))
        viewModel.performInitialLoad()
        verify(moviesActivity).upcoming_movies_empty_state.visibility == View.VISIBLE
    }

}