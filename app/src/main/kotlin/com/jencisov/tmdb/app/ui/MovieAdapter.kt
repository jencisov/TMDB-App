package com.jencisov.tmdb.app.ui

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jencisov.tmdb.R
import com.jencisov.tmdb.app.ui.base.BaseDiffAdapter
import com.jencisov.tmdb.app.ui.base.VIEW_TYPE_NORMAL
import com.jencisov.tmdb.app.utils.Genres
import com.jencisov.tmdb.domain.models.Movie
import com.jencisov.tmdb.extensions.loadPoster
import kotlinx.android.synthetic.main.item_genre.view.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(var listener: ItemClickListener) : BaseDiffAdapter<Movie, RecyclerView.ViewHolder>() {
    interface ItemClickListener {
        fun onItemClicked(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NORMAL)
            MovieViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_movie, parent, false)
            )
        else LoadingViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_loading, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_NORMAL) {
            val movie = getItem(position)
            val viewHolder = holder as MovieViewHolder
            viewHolder.posterTv.loadPoster(movie?.getPosterPath())
            viewHolder.titleTv.text = movie?.getTitle()
            viewHolder.releaseDateTv.text = movie?.getReleaseDate()
            setupGenres(viewHolder.genresLl, movie?.getGenres())
            viewHolder.itemView.setOnClickListener({ v -> listener.onItemClicked(movie!!) })
        }
    }

    fun setupGenres(container: LinearLayout, genres: List<Int>?) {
        if (genres == null || genres.isEmpty()) {
            return
        }

        container.removeAllViews()
        for (genreId in genres) {
            val genreText = Genres.getGenreById(genreId)
            if (genreText.isNullOrBlank()) {
                return
            }

            val genreCv = inflate(container.context, R.layout.item_genre, null) as CardView
            val genreTv = genreCv.item_genre_tv
            genreTv.text = genreText.toString()
            container.addView(genreCv)
        }
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val posterTv = view.item_movie_poster_iv
        val titleTv = view.item_movie_title_tv
        val releaseDateTv = view.item_movie_release_date_tv
        val genresLl = view.item_movie_genres_ll
    }

}