package com.jencisov.tmdb.domain.models

import android.os.Parcel
import android.os.Parcelable

data class Movie(
        var id: Long?,
        var posterPath: String?,
        var title: String?,
        var releaseDate: String?,
        var overview: String?,
        var genres: List<Int>?) : Parcelable {

    constructor() : this(null, null, null, null, null, null)

    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            arrayListOf<Int>().apply {
                parcel.readList(this, Int::class.java.classLoader)
            }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(posterPath)
        parcel.writeString(title)
        parcel.writeString(releaseDate)
        parcel.writeString(overview)
        parcel.writeList(genres)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

}