package com.inetkr.base.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import com.inetkr.base.data.source.share_preference.SharePref
import com.inetkr.base.presentation.base.BaseViewModel
import com.inetkr.base.utils.helper.Event
import com.inetkr.base.utils.helper.ObjectEventChannel
import kotlinx.coroutines.launch

class MainViewModel(private val sharePref: SharePref) : BaseViewModel() {
    var tokenExpire = MutableLiveData<Event.TokenExpire>()
    var updateApp = MutableLiveData<Event.UpdateApp>()

    init {
        scope.launch {
            ObjectEventChannel.eventFlow.collect { event ->
                if (event is Event.TokenExpire) {
                    tokenExpire.value = event
                    sharePref.logout()
                } else if (event is Event.UpdateApp) {
                    updateApp.value = event
                }
            }
        }
    }

}