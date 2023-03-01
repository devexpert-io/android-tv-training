package com.devepxerto.androidtvtraining.ui.main

import android.os.Bundle
import android.view.View
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.devepxerto.androidtvtraining.R

class MainFragment : BrowseSupportFragment() {

    data class Movie(val title: String, val year: Int, val poster: String)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = getString(R.string.browse_title)

        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter()

        (1..5).forEach { categoryId ->
            val categoryTitle = "Category $categoryId"

            val listRowAdapter = ArrayObjectAdapter(cardPresenter).apply {
                addAll(
                    0,
                    (1..10).map {
                        Movie(
                            "Title $it",
                            2020,
                            "https://loremflickr.com/240/320/cat?lock=$it"
                        )
                    })
            }

            val header = HeaderItem(categoryId.toLong(), categoryTitle)
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }

        adapter = rowsAdapter
    }
}