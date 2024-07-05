package com.inetkr.base.presentation.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.inetkr.base.presentation.base.custom.LoadingDialog
import com.inetkr.base.presentation.base.entity.BaseError
import com.inetkr.base.presentation.base.entity.BaseListLoadMoreResponse
import com.inetkr.base.presentation.base.entity.BaseListResponse
import com.inetkr.base.presentation.base.entity.BaseObjectResponse
import com.inetkr.base.utils.Define
import com.inetkr.base.utils.extensions.gone
import com.inetkr.base.utils.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseFragment<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) :
    Fragment(),
    BaseViewGroup< B>,
    ProgressBarManager {

    override lateinit var binding: B
    abstract fun backFromAddFragment()
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()
    abstract fun backPressed(): Boolean
    private var viewController: ViewController? = null
    protected var mSavedInstanceState: Bundle? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        binding = bindingFactory(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSavedInstanceState = savedInstanceState
        initView()
        initListener()
        initData()
    }


    fun setVC(viewController: ViewController) {
        this.viewController = viewController
    }

    fun getVC(): ViewController {
        if (viewController == null) {
            viewController = (activity as BaseActivity<*>).getViewController()
        }

        return viewController!!
    }

    protected open fun handleListResponse(response: BaseListResponse<*>) {
        when (response.type) {
            Define.ResponseStatus.LOADING -> showLoading()
            Define.ResponseStatus.SUCCESS -> {
                hideLoading()
                getListResponse(response.data)
            }

            Define.ResponseStatus.ERROR -> {
                hideLoading()
                if (response.isShowingError) {
                    handleNetworkError(response.error, true)
                } else {
                    if (response.error is BaseError) {
                        handleValidateError(response.error)
                    }
                }
            }
        }
    }

    protected open fun <U> handleObjectResponse(response: BaseObjectResponse<U>) {
        when (response.type) {
            Define.ResponseStatus.LOADING -> showLoading()
            Define.ResponseStatus.SUCCESS -> {
                hideLoading()
                getObjectResponse(response.data)
            }

            Define.ResponseStatus.ERROR -> {
                hideLoading()
                if (response.isShowingError) {
                    handleNetworkError(response.error, true)
                } else {
                    if (response.error is BaseError) {
                        handleValidateError(response.error)
                    }
                }
            }
        }
    }

    protected open fun handleLoadMoreResponse(response: BaseListLoadMoreResponse<*>) {
        when (response.type) {
            Define.ResponseStatus.LOADING -> showLoading()
            Define.ResponseStatus.SUCCESS -> {
                getListResponse(response.data, response.isReFresh, response.isLoadMore)
                hideLoading()
            }

            Define.ResponseStatus.ERROR -> {
                hideLoading()
                if (response.isShowingError) {
                    handleNetworkError(response.error, true)
                } else {
                    if (response.error is BaseError) {
                        handleValidateError(response.error)
                    }
                }
            }
        }
    }

    open fun <U> getObjectResponse(data: U) {

    }

    open fun <U> getListResponse(data: List<U>?) {

    }

    protected open fun getListResponse(data: List<*>?, isRefresh: Boolean, canLoadmore: Boolean) {}

    protected fun showLoading() {
        try {
            progressBar?.visible() ?:kotlin.run {
                LoadingDialog.getInstance(requireContext())?.show()
            }
        } catch (e: Exception) {
            Log.i("ahuhu", e.toString())
        }
    }

    protected fun hideLoading() {
        try {
            progressBar?.gone() ?:kotlin.run {
                LoadingDialog.getInstance(requireContext())?.hidden()
            }
        } catch (e: Exception) {
            Log.i("ahuhu", e.toString())
        }
    }

    protected open fun handleNetworkError(throwable: Throwable?, isShowDialog: Boolean) {
        if (activity != null && activity is BaseActivity<*>) {
            (activity as? BaseActivity<*>?)?.handleNetworkError(
                throwable,
                isShowDialog
            )
        }
    }

    protected open fun handleValidateError(baseError: BaseError?) {}

    companion object {
        const val DATA_LOADED = "DATA-LOADED"
    }

}