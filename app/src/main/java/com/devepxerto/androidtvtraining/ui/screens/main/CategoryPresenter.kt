package com.devepxerto.androidtvtraining.ui.screens.main

import android.view.ViewGroup
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.devepxerto.androidtvtraining.common.loadDrawable
import com.devepxerto.androidtvtraining.domain.Category

class CategoryPresenter : Presenter() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {

        val cardView = ImageCardView(parent.context).apply {
            isFocusable = true
            isFocusableInTouchMode = true
            setMainImageDimensions(176, 176)
        }

        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val movie = item as Category
        with(viewHolder.view as ImageCardView) {
            titleText = movie.name
            mainImageView.loadDrawable(movie.icon)
        }

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        with(viewHolder.view as ImageCardView) {
            mainImage = null
        }
    }

}