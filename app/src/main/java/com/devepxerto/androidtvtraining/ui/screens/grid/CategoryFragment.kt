package com.devepxerto.androidtvtraining.ui.screens.grid

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.VerticalGridPresenter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devepxerto.androidtvtraining.R
import com.devepxerto.androidtvtraining.common.serializable
import com.devepxerto.androidtvtraining.data.MoviesRepository
import com.devepxerto.androidtvtraining.data.remote.RemoteConnection
import com.devepxerto.androidtvtraining.domain.Category
import com.devepxerto.androidtvtraining.ui.screens.common.MovieCollectionScreen
import com.devepxerto.androidtvtraining.ui.screens.main.BackgroundState
import com.devepxerto.androidtvtraining.ui.screens.common.presenters.CardPresenter
import kotlinx.coroutines.launch

class CategoryFragment : VerticalGridSupportFragment(), MovieCollectionScreen {

    override val backgroundState = BackgroundState(this)
    private lateinit var gridAdapter: ArrayObjectAdapter
    private val category: Category by lazy { requireActivity().intent.serializable(CategoryActivity.CATEGORY_EXTRA)!! }

    private val vm by viewModels<CategoryViewModel> {
        CategoryViewModelFactory(
            category,
            MoviesRepository(
                RemoteConnection.service,
                requireContext().getString(R.string.api_key)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gridAdapter = ArrayObjectAdapter(CardPresenter())
        adapter = gridAdapter
        gridPresenter = VerticalGridPresenter().apply {
            numberOfColumns = 5
        }
        title = category.name

        onItemViewClickedListener = requireContext().itemViewClickListener()
        setOnItemViewSelectedListener(requireContext().itemViewSelectedListener())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.state.collect { state ->
                    if (state.loading) progressBarManager.show() else progressBarManager.hide()
                    gridAdapter.clear()
                    gridAdapter.addAll(0, state.movies)
                }
            }
        }

        vm.onUiReady()
    }
}