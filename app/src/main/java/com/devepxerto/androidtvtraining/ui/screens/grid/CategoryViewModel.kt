package com.devepxerto.androidtvtraining.ui.screens.grid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devepxerto.androidtvtraining.data.MoviesRepository
import com.devepxerto.androidtvtraining.domain.Category
import com.devepxerto.androidtvtraining.domain.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(private val category: Category, private val repository: MoviesRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(UiState(loading = false, movies = emptyList()))
    val state = _state.asStateFlow()

    fun onUiReady() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            _state.update { it.copy(loading = false, movies = repository.getCategory(category)) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>
    )

}

@Suppress("UNCHECKED_CAST")
class CategoryViewModelFactory(
    private val category: Category,
    private val repository: MoviesRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(category, repository) as T
    }
}