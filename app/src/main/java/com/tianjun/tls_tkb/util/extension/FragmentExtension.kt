package com.tianjun.tls_tkb.util.extension

import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tianjun.tls_tkb.util.view.ViewHelper

fun Fragment.showToastIfEditTextIsEmpty(vararg editTexts: EditText): Boolean {
    if (ViewHelper.isNotFill(*editTexts)){
        Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show()
        return true
    }
    return false
}

fun Fragment.showMessageDialog(message : String){
    MaterialAlertDialogBuilder(requireContext())
        .setTitle("Thông Báo")
        .setMessage(message)
        .setPositiveButton("Đóng", null)
        .create()
        .show();
}