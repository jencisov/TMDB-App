package com.jencisov.tmdb.data.webservice

import com.jencisov.tmdb.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {

    private lateinit var mClient: OkHttpClient
    private lateinit var mService: TheMovieDbService

    fun getUpcomingMoviesService(): TheMovieDbService {
        synchronized(ApiFactory::class.java) {
            mService = createService()
        }

        return mService
    }

    private fun createService(): TheMovieDbService {
        val client = getClient()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TheMovieDbService::class.java)
    }

    private fun getClient(): OkHttpClient {
        synchronized(ApiFactory::class.java) {
            mClient = buildClient()
        }

        return mClient
    }

    private fun buildClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

}