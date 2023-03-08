package com.devepxerto.androidtvtraining.ui.screens.common

import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.devepxerto.androidtvtraining.domain.Movie
import com.devepxerto.androidtvtraining.ui.screens.common.presenters.CardPresenter

interface MovieRowScreen : MovieCollectionScreen {

    val rowsAdapter: ArrayObjectAdapter

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