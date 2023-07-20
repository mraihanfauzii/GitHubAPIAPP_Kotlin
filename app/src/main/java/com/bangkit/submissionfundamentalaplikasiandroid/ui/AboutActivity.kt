package com.bangkit.submissionfundamentalaplikasiandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.submissionfundamentalaplikasiandroid.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar?.title = getString(R.string.about)
    }
}