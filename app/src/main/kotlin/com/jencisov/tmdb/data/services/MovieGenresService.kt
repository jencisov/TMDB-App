package com.jencisov.tmdb.data.services

import com.jencisov.tmdb.BuildConfig
import com.jencisov.tmdb.data.client.Client
import com.jencisov.tmdb.data.endpoints.MovieGenresApi
import com.jencisov.tmdb.data.response.MovieGenresResponse
import io.reactivex.Single
import retrofit2.Response

class MovieGenresService {
    var api: MovieGenresApi = Client.retrofit.create(MovieGenresApi::class.java)

    fun getGenres(): Single<Response<MovieGenresResponse>> {
        return api.getMovieGenres(BuildConfig.API_KEY)
    }

}