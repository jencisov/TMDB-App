package com.jencisov.tmdb.data.webservice

import io.reactivex.Observable;
import com.jencisov.tmdb.data.webservice.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbService {

    @GET("upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): Observable<MoviesResponse>

}