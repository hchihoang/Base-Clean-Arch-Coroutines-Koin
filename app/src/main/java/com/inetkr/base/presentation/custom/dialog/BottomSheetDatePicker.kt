package com.inetkr.base.presentation.custom.dialog

import androidx.lifecycle.lifecycleScope
import com.inetkr.base.databinding.BottomSheetDatePickerBinding
import com.inetkr.base.presentation.base.dialog.BaseFullScreenDialog
import com.inetkr.base.presentation.custom.CustomDatePickerView
import com.inetkr.base.utils.BundleKey
import com.inetkr.base.utils.extensions.DATE_FORMAT_1
import com.inetkr.base.utils.extensions.onAvoidDoubleClick
import com.inetkr.base.utils.extensions.visible
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class BottomSheetDatePicker(val maxDate: Boolean? = false) :
    BaseFullScreenDialog<BottomSheetDatePickerBinding>() {

    var valueSelected: String = ""
    private var formatter = SimpleDateFormat(DATE_FORMAT_1)
    private val calendar = Calendar.getInstance()

    var onclickDatePicker: (String) -> Unit = {}

    override fun getLayoutBinding(): BottomSheetDatePickerBinding {
        return BottomSheetDatePickerBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.clBottomSheet.visible()
        val screenHeight = resources.displayMetrics.heightPixels
        binding.clBottomSheet.translationY = screenHeight.toFloat()
        binding.clBottomSheet.animate()
            .translationY(0f)
            .setDuration(600)
            .start()

        // When you want update date selected, use bundle to do that
        arguments.let {
            arguments?.let {
                if (it.containsKey(BundleKey.KEY_DATE_SELECTED)) {
                    val dateSelected = it.getString(BundleKey.KEY_DATE_SELECTED)
                    if (!dateSelected.isNullOrEmpty()) {
                        setDateSelected(dateSelected)
                    } else {
                        formatter.format(calendar.time)
                    }
                }
            }
        }

        setVibrationFeedback(false)
    }

    fun setCircularPicker(isChecked: Boolean) {
        binding.dayTimePickerView.isCircular = isChecked
    }

    fun setVibrationFeedback(isChecked: Boolean) {
        binding.dayTimePickerView.isHapticFeedbackEnabled = isChecked
    }

    fun setDateSelected(value: String) {
        val date = formatter.parse(value)
        val calendarSelected = Calendar.getInstance()
        date?.let {
            calendarSelected.time = it
            binding.dayTimePickerView.setDate(
                calendarSelected.get(Calendar.YEAR),
                calendarSelected.get(Calendar.MONTH),
                calendarSelected.get(Calendar.DAY_OF_MONTH)
            )
        }
        // yyyy-MM-dd
        if (maxDate == true) {
            binding.dayTimePickerView.maxDate = calendar.time
        }
    }

    override fun initListener() {
        binding.containerBottomSheet.onAvoidDoubleClick {
            hideBottomSheet()
        }
        binding.dayTimePickerView.maxDate = Calendar.getInstance().time
        binding.dayTimePickerView.setWheelListener(object : CustomDatePickerView.Listener {
            override fun didSelectData(year: Int, month: Int, day: Int) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                valueSelected = formatter.format(calendar.time)
            }
        })

        binding.tvOk.setOnClickListener {
            onclickDatePicker(valueSelected)
            hideBottomSheet()
        }

        binding.imvCancel.setOnClickListener {
            hideBottomSheet()
        }
    }

    private fun hideBottomSheet() {
        lifecycleScope.launch {
            delay(500)
            dismissAllowingStateLoss()
        }
        val screenHeight = resources.displayMetrics.heightPixels
        binding.clBottomSheet.animate()
            .translationY(screenHeight.toFloat())
            .setDuration(600)
            .start()
    }

}