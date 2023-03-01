package com.devepxerto.androidtvtraining.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val releaseDate: String,
    val summary: String,
    val poster: String,
    val backdrop: String
) : Parcelable