package com.jencisov.tmdb.app.movies.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jencisov.tmdb.R
import com.jencisov.tmdb.app.movies.viewholder.LoadingViewHolder
import com.jencisov.tmdb.app.movies.viewholder.MovieViewHolder
import com.jencisov.tmdb.data.database.entity.MovieEntity

class MoviesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mMoviesList: MutableList<MovieEntity> = ArrayList()

    fun setList(list: List<MovieEntity>) {
        if (list.isNotEmpty()) {
            list.toMutableList().add(MovieEntity())
            mMoviesList = list.toMutableList()
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun getOldListSize() = mMoviesList.size

                override fun getNewListSize() = list.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    mMoviesList[oldItemPosition].getId() == list[newItemPosition].getId()

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val match = list[newItemPosition]
                    val old = list[oldItemPosition]
                    return match != old
                }

            })

            mMoviesList.addAll(list)
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val isItemMovie = viewType == regularItem

        val view: View
        if (isItemMovie) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            return MovieViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            return LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == regularItem) {
            setupMovie(holder, position)
        }
    }

    private fun setupMovie(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = mMoviesList[position]
        (holder as MovieViewHolder).bind(movie)
    }

    override fun getItemCount() = mMoviesList.size

    override fun getItemViewType(position: Int) = if (position == itemCount - 1) lastItem else regularItem

    companion object {
        const val lastItem = -1
        const val regularItem = 0
    }

}