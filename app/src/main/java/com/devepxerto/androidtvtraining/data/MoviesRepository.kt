package com.devepxerto.androidtvtraining.data

import com.devepxerto.androidtvtraining.data.remote.RemoteService
import com.devepxerto.androidtvtraining.data.remote.toDomain
import com.devepxerto.androidtvtraining.domain.Movie

class MoviesRepository(
    private val remoteService: RemoteService,
    private val apiKey: String
) {

    suspend fun listPopularMovies(): List<Movie> = remoteService.listPopularMovies(apiKey)
        .results
        .map { it.toDomain() }

}