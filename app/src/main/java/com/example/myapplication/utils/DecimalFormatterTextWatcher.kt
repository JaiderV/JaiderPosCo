package com.example.myapplication.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.Locale

class DecimalFormatterTextWatcher (private val editText: EditText ) : TextWatcher{

    private val numberFormat: NumberFormat = NumberFormat.getNumberInstance(Locale.getDefault())

    init {
        numberFormat.maximumFractionDigits = 2
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        editText.removeTextChangedListener(this)

        // Eliminar los separadores de miles y formatear el monto
        val cleanString = s.toString().replace("[.,]".toRegex(), "")
        val parsed = cleanString.toDoubleOrNull() ?: 0.0
        val formatted = numberFormat.format(parsed)

        editText.setText(formatted)
        editText.setSelection(formatted.length)

        editText.addTextChangedListener(this)
    }
}