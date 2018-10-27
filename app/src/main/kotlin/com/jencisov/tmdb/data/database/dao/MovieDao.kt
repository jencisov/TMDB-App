package com.jencisov.tmdb.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.jencisov.tmdb.data.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY release_date DESC")
    fun loadAllMovies(): MutableList<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieEntity>)

}