package com.jencisov.tmdb.data.endpoints

import com.jencisov.tmdb.data.response.MovieGenresResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieGenresApi {

    @GET("genre/movie/list")
    fun getMovieGenres(
        @Query("api_key") apiKey: String
    ): Single<Response<MovieGenresResponse>>

}