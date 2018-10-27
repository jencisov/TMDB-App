package com.jencisov.tmdb.app.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.jencisov.tmdb.R
import com.jencisov.tmdb.app.ui.adapter.MovieAdapter
import com.jencisov.tmdb.app.ui.viewmodel.MovieViewModel
import com.jencisov.tmdb.app.utils.LastClick
import com.jencisov.tmdb.domain.models.Movie
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        setupViews()
        registerObservables()
    }

    override fun onDestroy() {
        viewModel.resetMoviesDownloadingCurrentPage()
        super.onDestroy()
    }

    override fun onBackPressed() {
        viewModel.resetMoviesDownloadingCurrentPage()
        super.onBackPressed()
    }

    private fun setupViews() {
        adapter = MovieAdapter(
                object : MovieAdapter.ItemClickListener {
                    override fun onItemClicked(movie: Movie, imageView: ImageView) {
                        if (LastClick.releaseTheClick()) {
                            MovieDetailActivity.launchActivity(this@MoviesActivity, movie, imageView)
                        }
                    }
                }
        )
        upcoming_movies_rv.adapter = adapter
        viewModel.performInitialLoad()
    }

    private fun registerObservables() {
        submitItems()

        viewModel.errorToastEvent.observe(this,
                Observer {
                    this.upcoming_movies_empty_state.visibility == View.VISIBLE
                }
        )

        viewModel.emptyVisibilityEvents.observe(this,
                Observer { show ->
                    if (show != null) {
                        val visibility = if (show.peek()) View.VISIBLE else View.GONE
                        this.upcoming_movies_empty_state.visibility = visibility
                    }
                }
        )

        viewModel.recyclerViewLoadingEvents.observe(this,
                Observer { show ->
                    if (show != null) {
                        adapter.loading = show.peek()
                    }
                })
    }

    fun submitItems() {
        viewModel.getItems()!!.subscribe(
                { items -> adapter.submitList(items) },
                { this.upcoming_movies_empty_state.visibility = View.VISIBLE }
        )
    }

}
