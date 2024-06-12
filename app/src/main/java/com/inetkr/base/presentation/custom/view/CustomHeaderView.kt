package com.inetkr.base.presentation.custom.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.inetkr.base.R
import com.inetkr.base.databinding.ViewCustomHeaderBinding
import com.inetkr.base.utils.DeviceUtil
import com.inetkr.base.utils.extensions.dpToPx
import com.inetkr.base.utils.extensions.gone
import com.inetkr.base.utils.extensions.invisible
import com.inetkr.base.utils.extensions.loadAvatar
import com.inetkr.base.utils.extensions.loadBackIcon
import com.inetkr.base.utils.extensions.setMargins
import com.inetkr.base.utils.extensions.visible

class CustomHeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomViewRelativeLayout<ViewCustomHeaderBinding>(
    context,
    attrs,
    defStyleAttr,
    ViewCustomHeaderBinding::inflate
) {

    var onLeftClick: () -> Unit = {}
    var onRightClick: () -> Unit = {}

    override fun getStyAbleRes() = R.styleable.CustomHeaderView!!

    override fun initView() {
        val lp = binding.containerHeader.layoutParams
        lp.height =
            resources.getDimensionPixelSize(R.dimen.height_top_bar) + DeviceUtil.getStatusBarHeight(
                context
            )
        binding.containerHeader.setPadding(0, DeviceUtil.getStatusBarHeight(context), 0, 0)
    }

    override fun initListener() {

    }

    override fun initData() {

    }

    override fun initDataFromStyledAttributes(attr: TypedArray?) {
        attr?.let {
            try {
                if (attr.hasValue(R.styleable.CustomHeaderView_leftDrawable)) {
                    binding.btnMenuLeft.visible()
                    binding.btnMenuLeft.setPadding(5.dpToPx, 3.dpToPx, 1.dpToPx, 3.dpToPx)
                    binding.btnMenuLeft.loadBackIcon(
                        attr.getDrawable(
                            R.styleable.CustomHeaderView_leftDrawable
                        )
                    )
                } else {
                    binding.btnMenuLeft.invisible()
                }

                if (attr.hasValue(R.styleable.CustomHeaderView_leftTitle)) {
                    binding.tvMenuLeft.text = attr.getString(R.styleable.CustomHeaderView_leftTitle)
                    binding.tvMenuLeft.visible()
                } else {
                    binding.tvMenuLeft.gone()
                }

                if (attr.hasValue(R.styleable.CustomHeaderView_rightDrawable)) {
                    binding.btnMenuRight.visible()
                    binding.btnMenuRight.loadAvatar(
                        attr.getDrawable(
                            R.styleable.CustomHeaderView_rightDrawable
                        )
                    )
                } else {
                    binding.btnMenuRight.invisible()
                }

                if (attr.hasValue(R.styleable.CustomHeaderView_rightTitle)) {
                    binding.tvMenuRight.text =
                        attr.getString(R.styleable.CustomHeaderView_rightTitle)
                    binding.tvMenuRight.visibility = VISIBLE
                } else {
                    binding.tvMenuRight.visibility = GONE
                }

                if (attr.hasValue(R.styleable.CustomHeaderView_title)) {
                    binding.tvHeaderTitle.visibility = VISIBLE
                    binding.tvHeaderTitle.text = attr.getText(R.styleable.CustomHeaderView_title)
                } else {
                    binding.tvHeaderTitle.visibility = GONE
                }

                if (attr.hasValue(R.styleable.CustomHeaderView_header_background)) {
                    setHeaderBackgroundColor(
                        attr.getColor(
                            R.styleable.CustomHeaderView_header_background,
                            ContextCompat.getColor(context, R.color.colorPrimary)
                        )
                    )
                }

                if (attr.hasValue(R.styleable.CustomHeaderView_rightTitleColor)) {
                    binding.tvMenuRight.setTextColor(
                        attr.getColor(
                            R.styleable.CustomHeaderView_rightTitleColor,
                            ContextCompat.getColor(context, R.color.text_black)
                        )
                    )
                }
                // Click listener
                binding.btnMenuLeft.setOnClickListener { onLeftClick() }
                binding.tvMenuLeft.setOnClickListener { onLeftClick() }
                binding.btnMenuRight.setOnClickListener { onRightClick() }
                binding.tvMenuRight.setOnClickListener { onRightClick() }
            } finally {
                attr.recycle()
            }
        }
    }

    private fun setHeaderBackgroundColor(color: Int) {
        binding.containerHeader.setBackgroundColor(color)
    }

    fun setTitleBackground(drawable: Int) {
        binding.tvHeaderTitle.setBackgroundResource(drawable)
    }

    fun setTitle(title: String) {
        binding.tvHeaderTitle.text = title
    }

    fun setRightTile(title: String?) {
        binding.tvMenuRight.text = title
    }

    fun setLeftDrawable(@DrawableRes id: Int) {
        binding.btnMenuLeft.visibility = VISIBLE
        binding.btnMenuLeft.setImageResource(id)
    }

    fun setRightDrawable(@DrawableRes id: Int) {
        binding.btnMenuRight.visibility = VISIBLE
        binding.btnMenuRight.setImageResource(id)
    }

    fun showRightDrawable(isShow: Boolean) {
        if (isShow) {
            binding.btnMenuRight.visible()
        } else {
            binding.btnMenuRight.invisible()
        }
    }

    fun showLeftDrawable(isShow: Boolean) {
        if (isShow) {
            binding.btnMenuLeft.visible()
        } else {
            binding.btnMenuLeft.invisible()
        }
    }

    fun setImageToRightTitle(@DrawableRes id: Int = 0) {
        binding.tvMenuRight.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
    }

    fun setPaddingBtnRight(isSet: Boolean) {
        if (!isSet) {
            binding.btnMenuRight.setPadding(0, 0, 0, 0)
        }
    }

    fun disableHeader(disable: Boolean = true) {
        binding.btnMenuLeft.isEnabled = !disable
        binding.tvMenuLeft.isEnabled = !disable
        binding.btnMenuRight.isEnabled = !disable
        binding.tvMenuRight.isEnabled = !disable
    }

    fun setColorTitleWhite() {
        binding.btnMenuLeft.imageTintList = ContextCompat.getColorStateList(context, R.color.white)
        binding.tvHeaderTitle.setTextColor(ContextCompat.getColorStateList(context, R.color.white))
    }

    fun setTitleLive() {
        binding.tvHeaderTitle.gravity = left
        binding.tvHeaderTitle.setMargins(right = 16.dpToPx)
    }

}