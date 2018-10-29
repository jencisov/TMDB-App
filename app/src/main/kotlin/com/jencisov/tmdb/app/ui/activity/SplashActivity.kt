package com.jencisov.tmdb.app.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jencisov.tmdb.R
import com.jencisov.tmdb.app.ui.viewmodel.SplashViewModel
import com.jencisov.tmdb.app.utils.LastClick
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.performInitialLoad(this)

        splash_retry_bt.setOnClickListener {
            if (LastClick.releaseTheClick()) {
                viewModel.performInitialLoad(this@SplashActivity)
            }
        }
    }

    fun displayError() {
        splash_error_tv.visibility = View.VISIBLE
        splash_retry_bt.visibility = View.VISIBLE
    }

    fun hideError() {
        splash_error_tv.visibility = View.GONE
        splash_retry_bt.visibility = View.GONE
    }

}