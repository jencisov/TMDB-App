package com.jencisov.tmdb.data.services

import com.jencisov.tmdb.BuildConfig
import com.jencisov.tmdb.data.endpoints.UpcomingMoviesApi
import com.jencisov.tmdb.data.response.MoviesResponse
import com.jencisov.tmdb.data.client.Client
import io.reactivex.Single
import retrofit2.Response

class MovieService {
    var api: UpcomingMoviesApi = Client.retrofit.create(UpcomingMoviesApi::class.java)

    fun getReposForUser(pageNumber: Int): Single<Response<MoviesResponse>> {
        return api.getRepos(BuildConfig.API_KEY, pageNumber)
    }

}