package com.devepxerto.androidtvtraining.ui.main

import android.content.Intent
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
import com.devepxerto.androidtvtraining.ui.detail.DetailActivity
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
    private val backgroundState = BackgroundState(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = getString(R.string.browse_title)
        rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = rowsAdapter

        onItemViewClickedListener =
            OnItemViewClickedListener { _, movie, _, _ ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra(DetailActivity.MOVIE_EXTRA, movie as Movie)
                }
                startActivity(intent)
            }

        onItemViewSelectedListener = OnItemViewSelectedListener { _, movie, _, _ ->
            (movie as? Movie)?.let { backgroundState.loadUrl(movie.backdrop) }
        }

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