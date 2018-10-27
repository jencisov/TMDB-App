package com.jencisov.tmdb.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private var _id: Long = -1,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private var _posterPath: String = "",
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private var _title: String = "",
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private var _releaseDate: String = "",
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private var _overview: String = "",
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private var _voteAverage: Double = 0.0
) : Comparable<MovieEntity> {

    fun getId() = _id

    fun getPosterPath(): String {
        return if (_posterPath == null) "" else _posterPath
    }

    fun getTitle() = _title

    fun getReleaseDate() = _releaseDate

    fun getOverview() = _overview

    fun getVoteAverage() = _voteAverage

    fun getFormattedReleaseDate(): Date {
        val datePatter = "yyyy-MM-dd"
        try {
            return SimpleDateFormat(datePatter).parse(getReleaseDate())
        } catch (e: Throwable) {
            return Date()
        }
    }

    fun setId(id: Long) {
        _id = id
    }

    fun setPosterPath(posterPath: String) {
        _posterPath = posterPath
    }

    fun setTitle(posterPath: String) {
        _title = posterPath
    }

    fun setReleaseDate(releaseDate: String) {
        _releaseDate = releaseDate
    }

    fun setOverview(overview: String) {
        _overview = overview
    }

    fun setVoteAverage(voteAverage: Double) {
        _voteAverage = voteAverage
    }

    override fun compareTo(other: MovieEntity): Int {
        val time = getFormattedReleaseDate().time
        val otherTime = other.getFormattedReleaseDate().time
        return when {
            time < otherTime -> 1
            time > otherTime -> -1
            else -> 0
        }
    }

}