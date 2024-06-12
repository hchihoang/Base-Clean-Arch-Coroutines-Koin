package com.inetkr.base.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import  com.inetkr.base.R

object DialogUtil {

    fun displayDialog(
        context: Context,
        message: String,
        action: () -> Unit,
        cancel: () -> Unit,
        isShowCancel : Boolean = true,
        strButtonOk: String? = null,
        strButtonCancel: String? = null,
    ) {
        val alertDialog = AlertDialog.Builder(context)
            .create()
        alertDialog.setTitle(context.getString(R.string.app_name))
        alertDialog.setCancelable(false)

        alertDialog.setMessage(message)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, strButtonOk ?: context.getString(android.R.string.ok)
        ) { dialog, _ ->
            action()
            dialog.dismiss()
        }
        if(isShowCancel){
            alertDialog.setButton(
                AlertDialog.BUTTON_NEGATIVE, strButtonCancel ?: context.getString(android.R.string.cancel)
            ) { dialog, _ ->
                cancel()
                dialog.dismiss()
            }
        }
        alertDialog.show()
    }

    fun displayDialogSettings(
        context: Context,
        message: String,
        cancelable: Boolean = true,
        dismissCompletion: (() -> Unit)? = null,
        action: () -> Unit
    ): AlertDialog {

        val alertDialog = AlertDialog.Builder(context)
            .create()
        alertDialog.setTitle(context.getString(R.string.app_name))
        alertDialog.setIcon(R.mipmap.ic_launcher_round)
        alertDialog.setCancelable(cancelable)

        alertDialog.setMessage(message)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, context.getString(android.R.string.ok)
        ) { _, _ ->
            run {
                action()
                alertDialog.dismiss()
            }
        }
        alertDialog.setOnDismissListener {
            dismissCompletion?.invoke()
        }
        return alertDialog
    }

    fun displayConfirmDialog(
        context: Context,
        message: String,
        cancelable: Boolean = true,
        dismissCompletion: (() -> Unit)? = null,
        action: () -> Unit
    ): AlertDialog {

        val alertDialog = AlertDialog.Builder(context)
            .create()

        alertDialog.setCancelable(cancelable)

        alertDialog.setMessage(message)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE,
            context.getString(android.R.string.ok)
        ) { _, _ ->
            run {
                action()
                alertDialog.dismiss()
            }
        }

        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE,
            context.getString(android.R.string.cancel)
        ) { _, _ ->
            run {
                alertDialog.dismiss()
            }
        }
        alertDialog.setOnDismissListener {
            dismissCompletion?.invoke()
        }
        return alertDialog
    }

    fun displayAlertDialog(
        context: Context,
        title: String,
        message: String,
        cancelable: Boolean = true
    ) {

        val alertDialog = AlertDialog.Builder(context)
            .create()
        alertDialog.setTitle(title)
        alertDialog.setCancelable(cancelable)

        alertDialog.setMessage(message)
        alertDialog.show()
    }

    fun displayAlertDialog(
        context: Context,
        layoutId: Int
    ) {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setCancelable(true)
        val view = LayoutInflater.from(context).inflate(layoutId, null)
        alertDialog.setView(view)
        alertDialog.show()
    }

    fun showDialogUpdateApp(context: Context, title: String, action:()->Unit) : AlertDialog{
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle(context.getString(R.string.msg_title_update_app))
        alertDialog.setIcon(R.mipmap.ic_launcher_round)
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton(context.getString(android.R.string.ok),null)
        alertDialog.setMessage(title)

        val mAlert = alertDialog.create()
        mAlert.setOnShowListener {
            val btnPositive = mAlert.getButton(AlertDialog.BUTTON_POSITIVE)
            btnPositive.setOnClickListener {
                action()
            }
        }
        return  mAlert
    }


}