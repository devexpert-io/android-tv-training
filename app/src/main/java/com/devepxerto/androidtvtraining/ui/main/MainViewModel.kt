package com.devepxerto.androidtvtraining.ui.main

import androidx.lifecycle.ViewModel
import com.devepxerto.androidtvtraining.domain.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState(emptyList()))
    val state = _state.asStateFlow()

    fun onUiReady() {
        _state.value = UiState((1..10).map {
            Movie(
                "Title $it",
                2020,
                "https://loremflickr.com/240/320/cat?lock=$it"
            )
        })
    }

    data class UiState(
        val movies: List<Movie>
    )
}