package com.devepxerto.androidtvtraining.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {
    @GET("discover/movie")
    suspend fun listPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("vote_count.gte") voteCount: Int = 100,
        @Query("page") page: Int = 1

    ): RemoteResult

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): RemoteResult

}