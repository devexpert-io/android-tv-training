package com.devepxerto.androidtvtraining.ui.screens.common

import android.content.Context
import android.widget.Toast
import androidx.leanback.widget.*
import com.devepxerto.androidtvtraining.domain.Category
import com.devepxerto.androidtvtraining.domain.Movie
import com.devepxerto.androidtvtraining.ui.screens.detail.DetailActivity
import com.devepxerto.androidtvtraining.ui.screens.main.BackgroundState
import com.devepxerto.androidtvtraining.ui.screens.main.CardPresenter

interface MovieRowScreen {

    val backgroundState: BackgroundState
    val rowsAdapter: ArrayObjectAdapter

    fun Context.itemViewClickListener() = OnItemViewClickedListener { vh, item, _, _ ->
        when (item) {
            is Movie -> navigateToDetail(vh, item)
            is Category -> navigateToCategory(item)
        }
    }

    fun Context.navigateToCategory(category: Category) {
        Toast.makeText(this, "${category.name} Clicked", Toast.LENGTH_SHORT).show()
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

    fun submitRows(vararg listRow: ListRow) {
        rowsAdapter.clear()
        rowsAdapter.addAll(0, listRow.asList())
    }

    fun buildListRow(id: Int, title: String, movieList: List<Movie>): ListRow {
        val adapter = ArrayObjectAdapter(CardPresenter())
        adapter.addAll(0, movieList)
        return ListRow(id.toLong(), HeaderItem(title), adapter)
    }
}