package com.jencisov.tmdb.app.ui.activity

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import android.transition.Slide
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.jencisov.tmdb.BuildConfig
import com.jencisov.tmdb.R
import com.jencisov.tmdb.app.utils.Genres
import com.jencisov.tmdb.domain.models.Movie
import com.jencisov.tmdb.extensions.loadPoster
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.item_genre.view.*

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val IMAGE = "IMAGE"
        const val MOVIE_EXTRA = "MOVIE_EXTRA"

        fun launchActivity(activity: Activity, movie: Movie, transitionImage: ImageView) {
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, IMAGE)
            ActivityCompat.startActivity(activity, intent, options.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareWindowForAnimation()
        setContentView(R.layout.activity_movie_detail)

        ViewCompat.setTransitionName(detail_movie_app_bar, IMAGE)

        val toolbar = detail_movie_toolbar as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val movie = intent.getParcelableExtra(MOVIE_EXTRA) as Movie
        setupViews(movie)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupViews(movie: Movie) {
        showToolbarTitle(getString(R.string.movie_details))
        detaiL_movie_appbar_iv.loadPoster(movie.posterPath, BuildConfig.POSTER_BIG_BASE_URL)
        detail_movie_title_tv.text = movie.title
        detail_movie_relase_date_tv.text = movie.releaseDate
        detail_movie_overview_tv.text = movie.overview
        setupGenres(movie.genres)
    }

    fun showToolbarTitle(title: String) {
        detail_movie_collapsing_toolbar.title = title
        detail_movie_collapsing_toolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))
    }

    fun setupGenres(genres: List<Int>?) {
        if (genres == null || genres.isEmpty()) {
            return
        }

        detail_movie_genres_ll.removeAllViews()
        for (genreId in genres) {
            val genreText = Genres.getGenreById(genreId)
            if (genreText.isNullOrBlank()) {
                return
            }

            val genreCv = View.inflate(detail_movie_genres_ll.context, R.layout.item_genre, null) as CardView
            val genreTv = genreCv.item_genre_tv
            genreTv.text = genreText.toString()
            detail_movie_genres_ll.addView(genreCv)
        }
    }

    private fun prepareWindowForAnimation() {
        val transition = Slide()
        transition.excludeTarget(android.R.id.statusBarBackground, true)
        window.statusBarColor = Color.TRANSPARENT
        window.enterTransition = transition
        window.returnTransition = transition
    }

}