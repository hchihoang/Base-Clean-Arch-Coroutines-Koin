package com.inetkr.base.presentation.ui.login

import com.inetkr.base.R
import com.inetkr.base.databinding.FragmentLoginBinding
import com.inetkr.base.domain.entity.response.LoginResponse
import com.inetkr.base.presentation.base.BaseFragment
import com.inetkr.base.presentation.ui.home.HomeFragment
import com.inetkr.base.presentation.ui.signup.SignUpFragment
import com.inetkr.base.utils.extensions.hideKeyboard
import com.inetkr.base.utils.extensions.onAvoidDoubleClick
import com.inetkr.base.utils.extensions.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment :
    BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModel()
    override fun backFromAddFragment() {

    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {
        binding.tvSignUp.onAvoidDoubleClick {
            getVC().addFragment(SignUpFragment::class.java)
        }
        binding.btnLogin.onAvoidDoubleClick {
            it.hideKeyboard()
            viewModel.login(
                binding.etUsername.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }

        with(viewModel) {
            loginData.observe(this@LoginFragment) { response ->
                handleObjectResponse(response)
            }
        }
    }

    override fun <U> getObjectResponse(data: U) {
        if (data is LoginResponse) {
            toast(getString(R.string.str_login_success))
            getVC().replaceFragment(HomeFragment::class.java)
        }
    }

    override fun backPressed(): Boolean {
        return true
    }

}