package com.inetkr.base.utils

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.core.content.ContextCompat
import com.inetkr.base.R

class TermsAndPrivacyTextBuilder(private val context: Context) {
    private var privacyPolicyText: String = context.getString(R.string.privacy_policy)
    private var termsOfUseText: String = context.getString(R.string.terms_of_use)
    private val andText: String = context.getString(R.string.language_and)
    var onPrivacyPolicyClick: (() -> Unit) = {}
    var onTermsOfUseClick: (() -> Unit) = {}

    fun build(): SpannableString {
        val text = "$termsOfUseText $andText $privacyPolicyText"
        val spannableString = SpannableString(text)

        val privacyClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: android.view.View) {
                onPrivacyPolicyClick()
            }
        }

        val termsClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: android.view.View) {
                onTermsOfUseClick()
            }
        }

        // Set clickable spans
        spannableString.setSpan(
            termsClickableSpan,
            0,
            termsOfUseText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            privacyClickableSpan,
            termsOfUseText.length + 1 + andText.length + 1,
            text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Set foreground color
        val redColor = ForegroundColorSpan(ContextCompat.getColor(context, R.color.md_red_500))
        val defaultColor =
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.grey_subtitle))
        spannableString.setSpan(
            redColor,
            0,
            text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            defaultColor,
            termsOfUseText.length,
            termsOfUseText.length + 1 + andText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Set bold style
        val boldStyle = StyleSpan(android.graphics.Typeface.BOLD)
        spannableString.setSpan(
            boldStyle,
            0,
            text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        // Set default style
        val defaultStyle = StyleSpan(android.graphics.Typeface.NORMAL)
        spannableString.setSpan(
            defaultStyle,
            termsOfUseText.length,
            termsOfUseText.length + 1 + andText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spannableString
    }
}