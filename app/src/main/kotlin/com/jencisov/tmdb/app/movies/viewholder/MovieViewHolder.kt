package com.jencisov.tmdb.app.movies.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jencisov.tmdb.R
import com.jencisov.tmdb.data.database.entity.MovieEntity
import com.jencisov.tmdb.extensions.loadPoster

class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val titleTv: TextView by lazy {
        view.findViewById(R.id.item_movie_title_tv) as TextView
    }
    private val releaseDateTv: TextView by lazy {
        view.findViewById(R.id.item_movie_release_date_tv) as TextView
    }
    private val posterIv: ImageView by lazy {
        view.findViewById(R.id.item_movie_poster_iv) as ImageView
    }
    private val overviewTv: TextView by lazy {
        view.findViewById(R.id.item_movie_overview_tv) as TextView
    }
    private val ratingTv: TextView by lazy {
        view.findViewById(R.id.item_movie_rating_tv) as TextView
    }

    fun bind(movie: MovieEntity) {
        with(movie) {
            titleTv.text = getTitle()
            releaseDateTv.text = getReleaseDate()
            overviewTv.text = getOverview()
            if (getVoteAverage() > 0) {
                ratingTv.text = getVoteAverage().toString()
            } else {
                ratingTv.text = view.context.getString(R.string.no_rating)
            }
            posterIv.loadPoster(getPosterPath())
        }
    }

}