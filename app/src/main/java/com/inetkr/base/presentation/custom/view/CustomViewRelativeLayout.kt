package com.inetkr.base.presentation.custom.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewbinding.ViewBinding

abstract class CustomViewRelativeLayout<VB : ViewBinding>(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : RelativeLayout(context, attrs, defStyleAttr) {

    protected lateinit var binding: VB

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        binding = bindingFactory(LayoutInflater.from(context), this, true)
        initView()
        initListener()
        initData()
        attrs?.let {
            val styledAttributes =
                getStyAbleRes()?.let { it1 ->
                    context.obtainStyledAttributes(it, it1, 0, 0)
                }
            initDataFromStyledAttributes(styledAttributes)
        }
    }

    protected abstract fun getStyAbleRes(): IntArray?
    protected abstract fun initView()
    protected abstract fun initListener()
    protected abstract fun initData()
    protected abstract fun initDataFromStyledAttributes(styledAttributes: TypedArray?)
}
