package com.jencisov.tmdb.app.utils

import android.os.SystemClock

object LastClick {
    private var mLastClickTime: Long = 0

    fun releaseTheClick(): Boolean {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return false
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return true
    }

}