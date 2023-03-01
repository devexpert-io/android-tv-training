package com.devepxerto.androidtvtraining.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devepxerto.androidtvtraining.common.loadImageUrl
import com.devepxerto.androidtvtraining.common.parcelable
import com.devepxerto.androidtvtraining.ui.detail.DetailActivity.Companion.MOVIE_EXTRA
import kotlinx.coroutines.launch

class DetailFragment : DetailsSupportFragment() {

    private val detailsBackgroundState = DetailsBackgroundState(this)

    private val vm by viewModels<DetailViewModel> {
        DetailViewModelFactory(requireActivity().intent.parcelable(MOVIE_EXTRA)!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val presenter = FullWidthDetailsMovieRowPresenter(requireActivity())
        val rowsAdapter = ArrayObjectAdapter(presenter)
        adapter = rowsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.state.collect { (movie) ->
                    val row = DetailsOverviewRow(movie)
                    row.loadImageUrl(requireContext(), movie.poster)
                    row.actionsAdapter = presenter.buildActions()
                    rowsAdapter.add(0, row)

                    detailsBackgroundState.loadUrl(movie.backdrop)
                }
            }
        }
    }
}