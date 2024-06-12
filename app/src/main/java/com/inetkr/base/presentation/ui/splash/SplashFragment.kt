package com.inetkr.base.presentation.ui.splash

import android.os.Handler
import android.os.Looper
import com.inetkr.base.databinding.FragmentSplashBinding
import com.inetkr.base.presentation.base.BaseFragment
import com.inetkr.base.presentation.ui.home.HomeFragment
import com.inetkr.base.presentation.ui.login.LoginFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment :
    BaseFragment<SplashViewModel, FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override val viewModel: SplashViewModel by viewModel()
    override fun backFromAddFragment() {

    }

    override fun initView() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (viewModel.isUserLogin()) {
                getVC().replaceFragment(HomeFragment::class.java)
            } else {
                getVC().replaceFragment(LoginFragment::class.java)
            }
        }, 1500)
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun backPressed(): Boolean {
        return false
    }

}