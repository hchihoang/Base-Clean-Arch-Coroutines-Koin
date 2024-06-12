package com.inetkr.base.utils.helper

import android.content.Context
import com.inetkr.base.data.source.share_preference.SharePrefImpl
import java.util.Locale

object LocaleHelper {

    fun onAttach(context: Context?): Context? {
        context?.let {
            val lang = SharePrefImpl(context).language
            if (lang.isEmpty())
                return context
            else
                return updateResources(
                    context,
                    lang
                )
        } ?: kotlin.run {
            return null
        }
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        context.resources.configuration.setLocale(locale)
        context.resources.configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(context.resources.configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        context.resources.configuration.locale = locale
        context.resources.configuration.setLayoutDirection(locale)
        context.resources.updateConfiguration(
            context.resources.configuration,
            context.resources.displayMetrics
        )

        return context
    }
}