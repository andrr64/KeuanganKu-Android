package com.andreasoftware.keuanganku.ui

import androidx.recyclerview.widget.DiffUtil
import com.andreasoftware.keuanganku.data.sqlite.entities.Income

class IncomeDiffCallback : DiffUtil.ItemCallback<Income>() {
    override fun areItemsTheSame(oldItem: Income, newItem: Income): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Income, newItem: Income): Boolean {
        return oldItem == newItem
    }
}
