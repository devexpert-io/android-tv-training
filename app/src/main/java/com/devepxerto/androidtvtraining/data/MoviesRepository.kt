package com.devepxerto.androidtvtraining.data

import com.devepxerto.androidtvtraining.data.remote.RemoteService
import com.devepxerto.androidtvtraining.data.remote.toDomain
import com.devepxerto.androidtvtraining.domain.Category
import com.devepxerto.androidtvtraining.domain.Movie

class MoviesRepository(
    private val remoteService: RemoteService,
    private val apiKey: String
) {

    suspend fun getCategories(): Map<Category, List<Movie>> {
        return Category.values().associateWith { category ->
            remoteService.listPopularMovies(apiKey, category.id).results.map { it.toDomain() }
        }
    }

}