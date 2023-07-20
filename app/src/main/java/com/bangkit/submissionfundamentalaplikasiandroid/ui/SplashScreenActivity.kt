package com.bangkit.submissionfundamentalaplikasiandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bangkit.submissionfundamentalaplikasiandroid.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
        supportActionBar?.hide()
    }

    companion object {
        const val SPLASH_TIME_OUT: Long = 2000 //2 seconds
    }
}