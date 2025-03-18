package com.andreasoftware.keuanganku.ui.common

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

object Adapter {
    fun <T> setupDropdownAdapter(
        context: Context,
        items: List<T>,
        displayText: List<String>,
        autoCompleteTextView: AutoCompleteTextView,
        onItemSelected: (T) -> Unit
    ) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, displayText)
        autoCompleteTextView.setAdapter(adapter)

        if (items.isNotEmpty()) {
            autoCompleteTextView.setText(displayText[0], false)
            onItemSelected(items[0])
        }

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            onItemSelected(items[position])
            autoCompleteTextView.setText(displayText[position], false)
        }
    }

}