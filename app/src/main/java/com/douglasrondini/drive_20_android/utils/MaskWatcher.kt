package com.douglasrondini.drive_20_android.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MaskWatcher(private val mask: String, private val editText: EditText) : TextWatcher {
    private var isUpdating: Boolean = false
    private var oldString: String = ""

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val str = unmask(s.toString())
        var formatted = ""
        
        if (isUpdating) {
            oldString = str
            isUpdating = false
            return
        }

        var i = 0
        for (m in mask.toCharArray()) {
            if (m != '#' && str.length > oldString.length) {
                formatted += m
                continue
            }
            try {
                formatted += str[i]
            } catch (e: Exception) {
                break
            }
            i++
        }

        isUpdating = true
        editText.setText(formatted)
        editText.setSelection(formatted.length)
    }

    override fun afterTextChanged(s: Editable?) {}

    companion object {
        fun unmask(s: String): String {
            return s.replace("[^0-9a-zA-Z]".toRegex(), "")
        }

        fun applyMask(mask: String, editText: EditText) {
            editText.addTextChangedListener(MaskWatcher(mask, editText))
        }

        const val MASK_CPF = "###.###.###-##"
        const val MASK_PHONE = "(##) #####-####"
        const val MASK_CNH = "###########" // CNH geralmente é apenas números, mas fixo 11
        const val MASK_PLATE = "#######" // Placa Mercosul tem letras e números misturados
    }
}
