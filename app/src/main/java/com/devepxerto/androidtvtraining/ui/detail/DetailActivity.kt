package com.devepxerto.androidtvtraining.ui.detail

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.devepxerto.androidtvtraining.R

class DetailActivity : FragmentActivity() {
    companion object {
        const val MOVIE_EXTRA = "extra:movie"
        const val HERO_EXTRA = "extra:hero"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}