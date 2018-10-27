package com.jencisov.tmdb.data.webservice.response

class Response<T> private constructor(val data: T?, val error: Throwable?) {

    companion object {
        fun <T> success(data: T): Response<T> {
            return Response(data, null)
        }

        fun <T> error(error: Throwable): Response<T> {
            return Response(null, error)
        }
    }

}