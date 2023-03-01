package com.devepxerto.androidtvtraining.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devepxerto.androidtvtraining.R
import com.devepxerto.androidtvtraining.common.loadImageUrl
import com.devepxerto.androidtvtraining.common.parcelable
import com.devepxerto.androidtvtraining.ui.detail.DetailActivity.Companion.MOVIE_EXTRA
import kotlinx.coroutines.launch

class DetailFragment : DetailsSupportFragment() {

    private enum class Options(@StringRes val stringRes: Int) {
        WATCH_TRAILER(R.string.watch_trailer),
        FAVORITE(R.string.favorite);
    }

    private val detailsBackgroundState = DetailsBackgroundState(this)

    private val vm by viewModels<DetailViewModel> {
        DetailViewModelFactory(requireActivity().intent.parcelable(MOVIE_EXTRA)!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val presenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())
        val rowsAdapter = ArrayObjectAdapter(presenter)
        adapter = rowsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.state.collect { (movie) ->
                    val row = DetailsOverviewRow(movie)
                    row.loadImageUrl(requireContext(), movie.poster)
                    row.actionsAdapter = buildActions(presenter)
                    rowsAdapter.add(0, row)

                    detailsBackgroundState.loadUrl(movie.backdrop)
                }
            }
        }
    }

    private fun buildActions(presenter: FullWidthDetailsOverviewRowPresenter): ArrayObjectAdapter {
        presenter.setOnActionClickedListener { action ->
            val option = Options.values().first { it.ordinal == action.id.toInt() }
            Toast.makeText(requireContext(), option.stringRes, Toast.LENGTH_SHORT).show()
        }

        return ArrayObjectAdapter().apply {
            Options.values().forEach { option ->
                add(Action(option.ordinal.toLong(), getString(option.stringRes)))
            }
        }
    }
}