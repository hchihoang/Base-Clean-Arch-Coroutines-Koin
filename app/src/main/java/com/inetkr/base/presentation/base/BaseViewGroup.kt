package com.inetkr.base.presentation.base

import androidx.viewbinding.ViewBinding

interface BaseViewGroup<B : ViewBinding> {

    var binding: B

}
