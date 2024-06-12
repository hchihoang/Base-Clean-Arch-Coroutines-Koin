package com.inetkr.base.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.cancel
import androidx.lifecycle.viewModelScope

open class BaseViewModel : ViewModel() {

    val scope = viewModelScope

    // Cancel the job when the view model is destroyed
    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}