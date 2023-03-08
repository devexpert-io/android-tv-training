package com.devepxerto.androidtvtraining.ui.screens.common

import android.content.Context
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.OnItemViewSelectedListener
import androidx.leanback.widget.Presenter
import com.devepxerto.androidtvtraining.domain.Category
import com.devepxerto.androidtvtraining.domain.Movie
import com.devepxerto.androidtvtraining.ui.screens.detail.DetailActivity
import com.devepxerto.androidtvtraining.ui.screens.grid.CategoryActivity
import com.devepxerto.androidtvtraining.ui.screens.main.BackgroundState

interface MovieCollectionScreen {

    val backgroundState: BackgroundState

    fun Context.itemViewClickListener() = OnItemViewClickedListener { vh, item, _, _ ->
        when (item) {
            is Movie -> navigateToDetail(vh, item)
            is Category -> CategoryActivity.navigate(this, item)
        }
    }

    fun Context.navigateToDetail(vh: Presenter.ViewHolder, movie: Movie) {
        DetailActivity.navigate(
            this,
            movie,
            (vh.view as ImageCardView).mainImageView
        )
    }

    fun Context.itemViewSelectedListener() = OnItemViewSelectedListener { _, movie, _, _ ->
        (movie as? Movie)?.let { backgroundState.loadUrl(movie.backdrop) }
    }
}