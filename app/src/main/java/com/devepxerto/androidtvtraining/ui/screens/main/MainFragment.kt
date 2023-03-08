package com.devepxerto.androidtvtraining.ui.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devepxerto.androidtvtraining.R
import com.devepxerto.androidtvtraining.data.MoviesRepository
import com.devepxerto.androidtvtraining.data.remote.RemoteConnection
import com.devepxerto.androidtvtraining.domain.Category
import com.devepxerto.androidtvtraining.domain.Movie
import com.devepxerto.androidtvtraining.ui.screens.common.MovieRowScreen
import com.devepxerto.androidtvtraining.ui.screens.common.presenters.CategoryPresenter
import com.devepxerto.androidtvtraining.ui.screens.search.SearchActivity
import kotlinx.coroutines.launch

class MainFragment : BrowseSupportFragment(), MovieRowScreen {

    override val rowsAdapter: ArrayObjectAdapter = ArrayObjectAdapter(ListRowPresenter())
    private val vm by viewModels<MainViewModel> {
        MainViewModelFactory(
            MoviesRepository(
                RemoteConnection.service,
                requireContext().getString(R.string.api_key)
            )
        )
    }
    override val backgroundState = BackgroundState(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = getString(R.string.browse_title)
        adapter = rowsAdapter

        setupSearch()

        onItemViewClickedListener = requireContext().itemViewClickListener()
        onItemViewSelectedListener = requireContext().itemViewSelectedListener()

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

    private fun setupSearch() {
        searchAffordanceColor = resources.getColor(R.color.accent, null)

        setOnSearchClickedListener {
            startActivity(Intent(requireContext(), SearchActivity::class.java))
        }
    }

    private fun update(categories: Map<Category, List<Movie>>) {
        val rows = categories.map { (category, movies) ->
            buildListRow(category.ordinal, category.name, movies)
        }
        submitRows(*rows.toTypedArray())
        addCategoriesRow()
    }

    private fun addCategoriesRow() {
        val adapter = ArrayObjectAdapter(CategoryPresenter())
        adapter.addAll(0, Category.values().toList())
        rowsAdapter.add(ListRow(HeaderItem(getString(R.string.categories)), adapter))
    }
}