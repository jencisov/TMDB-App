package com.jencisov.tmdb.app.utils

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun handle(action: (T) -> Unit) {
        if (!hasBeenHandled) {
            action(content)
            hasBeenHandled = true
        }
    }

    fun peek(): T = content

}