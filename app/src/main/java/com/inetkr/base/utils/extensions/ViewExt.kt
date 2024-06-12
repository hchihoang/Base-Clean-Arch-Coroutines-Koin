package com.inetkr.base.utils.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.inetkr.base.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun ViewGroup.inflate(@LayoutRes layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}

fun View.onAvoidDoubleClick(
    throttleDelay: Long = 800L,
    onClick: (View) -> Unit
) {
    setOnClickListener {
        onClick(this)
        isClickable = false
        postDelayed({ isClickable = true }, throttleDelay)
    }
}

fun View.onAvoidClickAndDoubleClick(
    scope: CoroutineScope = CoroutineScope(EmptyCoroutineContext),
    onClick: (View) -> Unit,
    onDoubleClick: (View) -> Unit
) {
    val clickDelay = 200L // milliseconds
    var job: Job? = null

    setOnClickListener {
        if (job?.isActive != true) {
            job = scope.launch {
                delay(clickDelay)
                onClick(this@onAvoidClickAndDoubleClick)
                job = null
            }
        } else {
            job?.cancel()
            onDoubleClick(this@onAvoidClickAndDoubleClick)
            job = null
        }
    }
}

infix fun TextView.textChangedListener(init: TextWatcherWrapper.() -> Unit) {
    val wrapper = TextWatcherWrapper().apply { init() }
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable) {
            wrapper.after?.invoke(p0)
        }

        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            wrapper.before?.invoke(p0, p1, p2, p3)
        }

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            wrapper.on?.invoke(p0, p1, p2, p3)
        }

    })
}

infix fun ViewPager.pageChangeListener(init: OnPageChangeListenerWrapper.() -> Unit) {
    val wrapper = OnPageChangeListenerWrapper().apply { init() }
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            wrapper.onPageScrollStateChanged?.invoke(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            wrapper.onPageScrolled?.invoke(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            wrapper.onPageSelected?.invoke(position)
        }

    })
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun TextInputEditText.showKeyboard(context: Context) {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)

}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

fun EditText.disablePaste() {
    val callback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menu?.removeItem(android.R.id.paste)
            menu?.removeItem(android.R.id.autofill)
            return true
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {}
    }
    this.customInsertionActionModeCallback = callback
    this.customSelectionActionModeCallback = callback
}

fun AppCompatEditText.passwordType() {
    inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
    typeface = Typeface.DEFAULT
}

fun ImageView.loadAvatar(url: Any?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.img_default)
        .error(R.drawable.img_default)
        .centerCrop()
        .into(this)
}

fun ImageView.loadImageUrlCircleCrop(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.img_image_default)
        .error(R.drawable.img_image_default)
        .circleCrop()
        .into(this)
}

fun ImageView.loadImageTestUrl(url: Any?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.img_image_default)
        .error(R.drawable.img_image_default)
        .into(this)
}

fun ImageView.loadBackIcon(url: Any?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_close_white)
        .error(R.drawable.ic_close_white)
        .centerCrop()
        .into(this)
}

fun View.setMargins(
    left: Int = this.marginLeft,
    top: Int = this.marginTop,
    right: Int = this.marginRight,
    bottom: Int = this.marginBottom,
) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        setMargins(left, top, right, bottom)
    }

}

fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    this.startActivity(intent)
}

fun Context.checkEmpty(
    textInputEditText: TextInputEditText, textInputLayout: TextInputLayout,
    errorMessage: String
) {
    textInputEditText.textChangedListener {
        after {
            if (it.toString().trim().isEmpty()) {
                textInputLayout.error = errorMessage
            } else {
                textInputLayout.error = null
                textInputLayout.isErrorEnabled = false
            }
        }
    }
}

fun Context.checkShowError(check: Boolean, textError: String?, textInputLayout: TextInputLayout) {
    if (check) {
        textInputLayout.error = textError
    } else {
        textInputLayout.error = null
        textInputLayout.isErrorEnabled = false
    }
}

fun Context.goToStore() {
    val url = "https://play.google.com/store/apps/details?id=com.inetkr.base"
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}