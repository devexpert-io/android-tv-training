package com.devepxerto.androidtvtraining.ui.search

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.leanback.app.SearchSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.ObjectAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devepxerto.androidtvtraining.R
import com.devepxerto.androidtvtraining.data.MoviesRepository
import com.devepxerto.androidtvtraining.data.remote.RemoteConnection
import com.devepxerto.androidtvtraining.ui.common.MovieRowScreen
import com.devepxerto.androidtvtraining.ui.main.BackgroundState
import kotlinx.coroutines.launch

class SearchFragment : SearchSupportFragment(),
    SearchSupportFragment.SearchResultProvider, MovieRowScreen {

    override val rowsAdapter: ArrayObjectAdapter = ArrayObjectAdapter(ListRowPresenter())
    override val backgroundState = BackgroundState(this)

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

        setOnItemViewClickedListener(requireContext().itemViewClickListener())
        setOnItemViewSelectedListener(requireContext().itemViewSelectedListener())

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.state.collect { state ->
                    submitRows(buildListRow(0, getString(R.string.search), state.searchResult))
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