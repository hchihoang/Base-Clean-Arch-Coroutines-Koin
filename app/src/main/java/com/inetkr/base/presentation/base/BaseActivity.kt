package com.inetkr.base.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.inetkr.base.R
import com.inetkr.base.domain.entity.request.RequestError
import com.inetkr.base.presentation.base.entity.BaseResponse
import com.inetkr.base.utils.Define
import com.inetkr.base.utils.DeviceUtil
import com.inetkr.base.utils.extensions.enableFullScreen
import com.inetkr.base.utils.extensions.toast
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseActivity<V : BaseViewModel, B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) :
    AppCompatActivity(), BaseViewGroup<V, B> {

    final override lateinit var binding: B
    abstract var frameContainerId: Int
    private var viewController: ViewController? = null
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()

    fun getViewController(): ViewController {
        if (viewController == null) {
            viewController = ViewController(frameContainerId, supportFragmentManager)

        }
        return viewController!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        viewController = ViewController(frameContainerId, supportFragmentManager)
        enableFullScreen()
        initView()
        initData()
        initListener()
    }

    override fun onBackPressed() {
        if (viewController != null && viewController?.currentFragment != null) {
            if (viewController?.currentFragment?.backPressed() == true) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    open fun handleNetworkError(throwable: Throwable?, isShowDialog: Boolean): RequestError? {
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
                    }

                } ?: run {
                    requestError.errorCode = (Define.Api.TIME_OUT)
                    requestError.errorMessage = (getString(R.string.error_place_holder))
                }
            } catch (e: IOException) {
                requestError.errorCode = (Define.Api.TIME_OUT)
                requestError.errorMessage = (getString(R.string.error_place_holder))
            } catch (e: IllegalStateException) {
                requestError.errorCode = (Define.Api.TIME_OUT)
                requestError.errorMessage = (getString(R.string.error_place_holder))
            } catch (e: JsonSyntaxException) {
                requestError.errorCode = (Define.Api.TIME_OUT)
                requestError.errorMessage = (getString(R.string.error_place_holder))
            } catch (e: Exception) {
                requestError.errorCode = (Define.Api.TIME_OUT)
                requestError.errorMessage = (getString(R.string.error_place_holder))
            }
        } else if (throwable is ConnectException || throwable is SocketTimeoutException || throwable is UnknownHostException || throwable is IOException) {
            if (DeviceUtil.hasConnection(this)) {
                requestError.errorCode = (Define.Api.TIME_OUT)
                requestError.errorMessage = (getString(R.string.str_time_out))
            } else {
                requestError.errorCode = (Define.Api.NO_NETWORK_CONNECTION)
                requestError.errorMessage = (getString(R.string.str_no_connection))
            }

        } else {
            requestError.errorCode = (Define.Api.UNKNOWN)
            requestError.errorMessage = (getString(R.string.error_place_holder))
        }
        if (isShowDialog) {
            requestError.errorMessage?.let {
                toast(it)
            }
        }
        return requestError
    }
}