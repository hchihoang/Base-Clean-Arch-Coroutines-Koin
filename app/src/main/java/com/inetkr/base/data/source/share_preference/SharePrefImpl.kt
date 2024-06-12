package com.inetkr.base.data.source.share_preference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.inetkr.base.domain.entity.response.LoginResponse
import com.inetkr.base.utils.extensions.clearAll
import com.inetkr.base.utils.extensions.setString

class SharePrefImpl(var context: Context) : SharePref {

    companion object {
        const val INTAK_PREF = "INTAK_PREF"
        const val INTAK_PREF_LANGUAGE = "INTAK_PREF_LANGUAGE"
        const val INTAK_PREF_USER = "INTAK_PREF_USER"
        const val INTAK_PREF_FCM_TOKEN = "INTAK_PREF_FCM_TOKEN"
        const val INTAK_LANGUAGE = "INTAK_LANGUAGE"
    }

    private var mPrefs: SharedPreferences? = null
    private var mPrefsLanguage: SharedPreferences? = null

    init {
        mPrefs = context.getSharedPreferences(INTAK_PREF, Context.MODE_PRIVATE)
        mPrefsLanguage = context.getSharedPreferences(INTAK_PREF_LANGUAGE, Context.MODE_PRIVATE)
    }

    override var fcmToken: String
        get() = mPrefs?.getString(INTAK_PREF_FCM_TOKEN, "") ?: ""
        set(value) {
            mPrefs?.setString(INTAK_PREF_FCM_TOKEN, (value))
        }

    override var savedUser: LoginResponse?
        get() = try {
            Gson().fromJson(
                mPrefs?.getString(INTAK_PREF_USER, null),
                LoginResponse::class.java
            )
        } catch (e: Exception) {
            null
        }
        set(value) {
            mPrefs?.setString(INTAK_PREF_USER, Gson().toJson(value))
        }

    override fun logout() {
        mPrefs?.clearAll()
    }

    override var language: String
        get() = mPrefsLanguage?.getString(INTAK_LANGUAGE, "") ?: ""
        set(value) {
            mPrefsLanguage?.setString(INTAK_LANGUAGE, (value))
        }

}