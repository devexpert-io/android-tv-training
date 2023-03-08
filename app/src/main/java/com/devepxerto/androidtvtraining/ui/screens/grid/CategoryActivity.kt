package com.devepxerto.androidtvtraining.ui.screens.grid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.devepxerto.androidtvtraining.R
import com.devepxerto.androidtvtraining.domain.Category

class CategoryActivity : FragmentActivity() {

    companion object {
        const val CATEGORY_EXTRA = "extra:category"

        fun navigate(context: Context, category: Category) {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra(CATEGORY_EXTRA, category)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)
    }
}