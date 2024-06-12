package com.inetkr.base.presentation.base.custom

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.inetkr.base.R

class LoadingDialog private constructor(private val context: Context?) {
    private var dialog: AlertDialog? = null

    init {
        if (context != null && !isShown) {
            val dialogBuilder = AlertDialog.Builder(
                context
            )
            val li = LayoutInflater.from(context)
            val dialogView = li.inflate(R.layout.dialog_loading, null)
            dialogBuilder.setView(dialogView)
            dialogBuilder.setCancelable(false)
            dialog = dialogBuilder.create()
            if (dialog?.window != null) {
                dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            dialog?.setCancelable(false)
            dialog?.setCanceledOnTouchOutside(false)
        }
    }

    fun show() {
        if (!isShown && dialog != null) {
            forceShown()
            dialog?.show()
        }
    }

    fun hidden() {
        if (isShown && dialog != null && dialog?.isShowing == true) {
            initialize()
            dialog?.dismiss()
        }
    }

    fun destroyLoadingDialog() {
        if (instance != null && instance!!.dialog != null) {
            instance!!.dialog!!.dismiss()
        }
        instance = null
    }

    companion object {
        private var isShown = false

        @SuppressLint("StaticFieldLeak")
        private var instance: LoadingDialog? = null
        fun getInstance(context: Context?): LoadingDialog? {
            if (instance == null) {
                instance = LoadingDialog(context)
            }
            return instance
        }

        private fun forceShown() {
            isShown = true
        }

        private fun initialize() {
            isShown = false
        }
    }
}
