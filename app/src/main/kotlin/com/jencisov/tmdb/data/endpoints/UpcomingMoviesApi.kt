package com.jencisov.tmdb.data.endpoints

import com.jencisov.tmdb.data.response.MoviesResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UpcomingMoviesApi {

    @GET("upcoming")
    fun getRepos(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): Single<Response<MoviesResponse>>

}