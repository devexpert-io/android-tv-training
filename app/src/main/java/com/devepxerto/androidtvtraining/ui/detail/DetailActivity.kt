package com.devepxerto.androidtvtraining.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.devepxerto.androidtvtraining.R
import com.devepxerto.androidtvtraining.domain.Movie

class DetailActivity : FragmentActivity() {
    companion object {
        const val MOVIE_EXTRA = "extra:movie"
        const val HERO_EXTRA = "extra:hero"

        fun navigate(context: Context, movie: Movie, hero: View) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                hero,
                HERO_EXTRA
            )
            context.startActivity(intent, options.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}