package com.devepxerto.androidtvtraining.domain

import androidx.annotation.DrawableRes
import com.devepxerto.androidtvtraining.R

enum class Category(val id: String, @DrawableRes val icon: Int) {
    POPULAR("popularity.desc", R.drawable.ic_category_popularity),
    NEW("release_date.desc", R.drawable.ic_category_new),
    VOTES("vote_average.desc", R.drawable.ic_category_votes),
    REVENUE("revenue.desc", R.drawable.ic_category_revenue)
}