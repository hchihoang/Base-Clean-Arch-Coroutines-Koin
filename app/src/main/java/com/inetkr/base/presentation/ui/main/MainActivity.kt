package com.inetkr.base.presentation.ui.main

import android.content.Context
import androidx.lifecycle.lifecycleScope
import com.inetkr.base.R
import com.inetkr.base.databinding.ActivityMainBinding
import com.inetkr.base.presentation.base.BaseActivity
import com.inetkr.base.presentation.base.custom.LoadingDialog
import com.inetkr.base.presentation.ui.login.LoginFragment
import com.inetkr.base.presentation.ui.splash.SplashFragment
import com.inetkr.base.utils.Define
import com.inetkr.base.utils.DialogUtil
import com.inetkr.base.utils.extensions.goToStore
import com.inetkr.base.utils.extensions.toast
import com.inetkr.base.utils.helper.LocaleHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override var frameContainerId: Int = R.id.container

    private val viewModel: MainViewModel by viewModel()

    override fun initView() {
        getViewController().addFragment(SplashFragment::class.java)
    }

    override fun initData() {

    }

    override fun initListener() {
        viewModel.tokenExpire.observe(this) {
            toast(R.string.token_expire)
            getViewController().replaceFragment(LoginFragment::class.java)
            lifecycleScope.launch {
                delay(600)
                LoadingDialog.getInstance(this@MainActivity)?.hidden()
            }
        }
        viewModel.updateApp.observe(this) {
            DialogUtil.showDialogUpdateApp(this, it.title) {
                if (it.code == Define.Api.Http.RESPONSE_CODE_UPDATE_APP) {
                    goToStore()
                } else {
                    finish()
                }
            }.show()
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }
}
