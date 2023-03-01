package com.devepxerto.androidtvtraining.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devepxerto.androidtvtraining.R
import com.devepxerto.androidtvtraining.domain.Movie
import kotlinx.coroutines.launch

class MainFragment : BrowseSupportFragment() {

    private lateinit var rowsAdapter: ArrayObjectAdapter
    private val vm by viewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = getString(R.string.browse_title)
        rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = rowsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.state.collect { state ->
                    update(state.movies)
                }
            }
        }

        vm.onUiReady()
    }

    private fun update(movies: List<Movie>) {
        val cardPresenter = CardPresenter()

        (1..5).forEach { categoryId ->
            val categoryTitle = "Category $categoryId"

            val listRowAdapter = ArrayObjectAdapter(cardPresenter).apply {
                addAll(0, movies)
            }

            val header = HeaderItem(categoryId.toLong(), categoryTitle)
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }
    }
}