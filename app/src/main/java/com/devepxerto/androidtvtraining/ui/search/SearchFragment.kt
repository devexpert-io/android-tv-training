package com.devepxerto.androidtvtraining.ui.search

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.leanback.app.SearchSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devepxerto.androidtvtraining.R
import com.devepxerto.androidtvtraining.data.MoviesRepository
import com.devepxerto.androidtvtraining.data.remote.RemoteConnection
import com.devepxerto.androidtvtraining.domain.Movie
import com.devepxerto.androidtvtraining.ui.detail.DetailActivity
import com.devepxerto.androidtvtraining.ui.main.CardPresenter
import kotlinx.coroutines.launch

class SearchFragment : SearchSupportFragment(),
    SearchSupportFragment.SearchResultProvider {

    private val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
    private lateinit var listRowAdapter: ArrayObjectAdapter

    private val vm: SearchViewModel by viewModels {
        SearchViewModelFactory(
            MoviesRepository(
                RemoteConnection.service,
                requireContext().getString(R.string.api_key)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSearchResultProvider(this)

        listRowAdapter = ArrayObjectAdapter(CardPresenter()).apply {
            val header = HeaderItem(getString(R.string.search))
            rowsAdapter.add(ListRow(header, this))
        }

        setOnItemViewClickedListener { vh, movie, _, _ ->
            DetailActivity.navigate(
                requireContext(),
                movie as Movie,
                (vh.view as ImageCardView).mainImageView
            )
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.state.collect { state ->
                    listRowAdapter.clear()
                    listRowAdapter.addAll(0, state.searchResult)
                }
            }
        }
    }

    override fun onQueryTextChange(newQuery: String): Boolean {
        vm.onSearch(newQuery)
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        vm.onSearch(query)
        return true
    }

    override fun getResultsAdapter(): ObjectAdapter {
        return rowsAdapter
    }
}