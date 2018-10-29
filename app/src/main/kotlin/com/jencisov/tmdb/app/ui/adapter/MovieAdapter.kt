package com.jencisov.tmdb.app.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jencisov.tmdb.BuildConfig
import com.jencisov.tmdb.R
import com.jencisov.tmdb.app.ui.base.BaseDiffAdapter
import com.jencisov.tmdb.app.ui.base.VIEW_TYPE_NORMAL
import com.jencisov.tmdb.app.utils.Genres
import com.jencisov.tmdb.domain.models.Movie
import com.jencisov.tmdb.extensions.loadPoster
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(var listener: ItemClickListener) : BaseDiffAdapter<Movie, RecyclerView.ViewHolder>() {
    interface ItemClickListener {
        fun onItemClicked(movie: Movie, imageView: ImageView)
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
            viewHolder.posterIv.loadPoster(movie?.posterPath, BuildConfig.POSTER_LITTLE_BASE_URL)
            viewHolder.titleTv.text = movie?.title
            viewHolder.releaseDateTv.text = movie?.title
            setupGenres(viewHolder.genresTv, movie?.genres)
            viewHolder.itemView.setOnClickListener { listener.onItemClicked(movie!!, viewHolder.posterIv) }
        }
    }

    private fun setupGenres(genreTv: TextView, genres: List<Int>?) {
        if (genres == null || genres.isEmpty()) {
            return
        }

        val genreStringBuilder = StringBuilder()
        for ((index, genreId) in genres.withIndex()) {
            val genreText = Genres.getGenreById(genreId)
            if (genreText.isNullOrBlank()) {
                return
            }

            genreStringBuilder.append(genreText)
            if (index < genres.size - 1) {
                genreStringBuilder.append(" - ")
            }
        }

        genreTv.text = genreStringBuilder.toString()
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val posterIv = view.item_movie_poster_iv
        val titleTv = view.item_movie_title_tv
        val releaseDateTv = view.item_movie_release_date_tv
        val genresTv = view.item_movie_genres_tv
    }

}