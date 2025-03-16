package com.andreasoftware.keuanganku.util

import android.content.Context
import com.andreasoftware.keuanganku.R

object RatingDescription {
    fun getDescription(context: Context, rating: Int): String {
        return when (rating) {
            1 -> context.getString(R.string.rating_desc_1)
            2 -> context.getString(R.string.rating_desc_2)
            3 -> context.getString(R.string.rating_desc_3)
            4 -> context.getString(R.string.rating_desc_4)
            5 -> context.getString(R.string.rating_desc_5)
            else -> context.getString(R.string.rating_desc_corrupt)
        }
    }
}
