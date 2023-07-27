package com.tianjun.tls_tkb.util.view

import android.content.Context
import android.widget.EditText

object ViewHelper {
    fun isNotFill(vararg editText: EditText) = editText.all { it.text.isBlank() }
}