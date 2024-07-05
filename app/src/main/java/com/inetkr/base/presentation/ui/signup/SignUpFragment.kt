package com.inetkr.base.presentation.ui.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.google.gson.GsonBuilder
import com.inetkr.base.R
import com.inetkr.base.databinding.FragmentSignUpBinding
import com.inetkr.base.domain.entity.request.RegisterErrorRequest
import com.inetkr.base.domain.entity.request.RequestError
import com.inetkr.base.domain.entity.response.RegisterResponse
import com.inetkr.base.presentation.base.BaseFragment
import com.inetkr.base.presentation.base.entity.BaseResponse
import com.inetkr.base.presentation.custom.dialog.BottomSheetDatePicker
import com.inetkr.base.presentation.ui.home.HomeFragment
import com.inetkr.base.utils.BundleKey
import com.inetkr.base.utils.Define
import com.inetkr.base.utils.TermsAndPrivacyTextBuilder
import com.inetkr.base.utils.extensions.checkShowError
import com.inetkr.base.utils.extensions.onAvoidDoubleClick
import com.inetkr.base.utils.extensions.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException


class SignUpFragment :
    BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val viewModel: SignUpViewModel by viewModel()
    override fun backFromAddFragment() {

    }

    override fun initView() {
        binding.customHeader.setColorTitleWhite()
        val termsAndPrivacyTextBuilder = TermsAndPrivacyTextBuilder(requireContext())
        val spannableString = termsAndPrivacyTextBuilder.build()
        binding.textViewDocuments.text = spannableString
        binding.textViewDocuments.movementMethod = LinkMovementMethod.getInstance()
        termsAndPrivacyTextBuilder.onTermsOfUseClick = {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
            startActivity(browserIntent)
        }
        termsAndPrivacyTextBuilder.onPrivacyPolicyClick = {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
            startActivity(browserIntent)
        }
    }

    override fun initData() {

    }

    override fun initListener() {
        binding.customHeader.onLeftClick = {
            backPressed()
        }
        binding.etBirthday.onAvoidDoubleClick {
            val bottomSheetDatePicker = BottomSheetDatePicker(maxDate = true)
            val bundle = Bundle().apply {
                putString(
                    BundleKey.KEY_DATE_SELECTED,
                    binding.etBirthday.text.toString()
                )
            }
            bottomSheetDatePicker.arguments = bundle
            bottomSheetDatePicker.show(childFragmentManager, "")
            bottomSheetDatePicker.onclickDatePicker = {
                binding.etBirthday.setText(
                    it
                )
            }
        }
        binding.btSignUp.onAvoidDoubleClick {
            viewModel.signUp(
                binding.etBirthday.text?.toString()?.trim(),
                binding.etEmail.text?.toString()?.trim(),
                binding.etPassword.text?.toString()?.trim(),
                binding.etPasswordConfirm.text?.toString()?.trim()
            )
        }
        with(viewModel) {
            registerData.observe(viewLifecycleOwner) { response ->
                handleObjectResponse(response)
            }
            registerErrorRequest.observe(viewLifecycleOwner) {
                showError(it)
            }
        }
    }

    override fun handleNetworkError(throwable: Throwable?, isShowDialog: Boolean) {
        var requestError = RequestError()
        if (throwable is HttpException) {
            val errorBody: String?
            try {
                throwable.response()?.errorBody()?.let {
                    errorBody = it.string()
                    val gson = GsonBuilder().create()
                    requestError = gson.fromJson(errorBody, RequestError::class.java)
                    if (requestError.errorMessage.isNullOrEmpty() || requestError.errorCode.isNullOrEmpty()) {
                        val errorResponse: BaseResponse? =
                            gson.fromJson(errorBody, BaseResponse::class.java)
                        requestError.errorCode = errorResponse?.status?.toString()
                        requestError.errorMessage = errorResponse?.msg
                        val registerErrorRequest: RegisterErrorRequest? =
                            gson.fromJson(
                                errorBody, RegisterErrorRequest::class.java
                            )
                        showError(registerErrorRequest)

                    }
                } ?: run {
                    requestError.errorCode = (Define.Api.TIME_OUT)
                    requestError.errorMessage = (getString(R.string.error_place_holder))
                }
            } catch (e: Exception) {
                requestError.errorCode = (Define.Api.TIME_OUT)
                requestError.errorMessage = (getString(R.string.error_place_holder))
            }
            super.handleNetworkError(throwable, false)
        } else {
            super.handleNetworkError(throwable, true)
        }
        if (isShowDialog) {
            requestError.errorMessage?.let {
                toast(it)
            }
        }

    }

    private fun showError(registerErrorRequest: RegisterErrorRequest?) {
        requireContext().checkShowError(
            registerErrorRequest?.registerError?.email?.isNotEmpty() ?: false,
            registerErrorRequest?.registerError?.email?.first(),
            binding.tilEmail
        )
        requireContext().checkShowError(
            registerErrorRequest?.registerError?.birthday?.isNotEmpty() ?: false,
            registerErrorRequest?.registerError?.birthday?.first(),
            binding.tilBirthday
        )
        requireContext().checkShowError(
            registerErrorRequest?.registerError?.password?.isNotEmpty() ?: false,
            registerErrorRequest?.registerError?.password?.first(),
            binding.tilPassword
        )
        requireContext().checkShowError(
            registerErrorRequest?.registerError?.confirmPassword?.isNotEmpty() ?: false,
            registerErrorRequest?.registerError?.confirmPassword?.first(),
            binding.tilPasswordConfirm
        )
    }

    override fun <U> getObjectResponse(data: U) {
        if (data is RegisterResponse) {
            toast(getString(R.string.sign_up_success))
            getVC().replaceFragment(HomeFragment::class.java)
        }
    }

    override fun backPressed(): Boolean {
        getVC().backFromAddFragment()
        return false
    }

}