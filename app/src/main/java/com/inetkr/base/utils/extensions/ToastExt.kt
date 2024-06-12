package com.inetkr.base.utils.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.inetkr.base.presentation.base.CommonToast

fun Context.toast(@StringRes resourceId: Int, length: Int = Toast.LENGTH_SHORT) {
    toast(getString(resourceId), length)
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    CommonToast.makeToast(this, message, length).show()
}

fun Fragment.toast(@StringRes resourceId: Int, length: Int = Toast.LENGTH_SHORT) {
    CommonToast.makeToast(requireContext(), getString(resourceId), length).show()
}

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    CommonToast.makeToast(requireContext(), message, length).show()
}