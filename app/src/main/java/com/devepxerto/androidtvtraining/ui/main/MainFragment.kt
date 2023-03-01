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
import com.devepxerto.androidtvtraining.data.MoviesRepository
import com.devepxerto.androidtvtraining.data.remote.RemoteConnection
import com.devepxerto.androidtvtraining.domain.Category
import com.devepxerto.androidtvtraining.domain.Movie
import kotlinx.coroutines.launch

class MainFragment : BrowseSupportFragment() {

    private lateinit var rowsAdapter: ArrayObjectAdapter
    private val vm by viewModels<MainViewModel> {
        MainViewModelFactory(
            MoviesRepository(
                RemoteConnection.service,
                requireContext().getString(R.string.api_key)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = getString(R.string.browse_title)
        rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = rowsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.state.collect { state ->
                    if (state.loading) progressBarManager.show() else progressBarManager.hide()
                    update(state.categories)
                }
            }
        }

        vm.onUiReady()
    }

    private fun update(categories: Map<Category, List<Movie>>) {
        val cardPresenter = CardPresenter()

        categories.forEach { (category, movies) ->

            val listRowAdapter = ArrayObjectAdapter(cardPresenter).apply {
                addAll(0, movies)
            }

            val header = HeaderItem(category.ordinal.toLong(), category.name)
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }
    }
}