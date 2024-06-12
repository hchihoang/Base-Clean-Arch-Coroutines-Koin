package com.inetkr.base.presentation.ui.home

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.inetkr.base.databinding.FragmentHomeBinding
import com.inetkr.base.domain.entity.response.Note
import com.inetkr.base.presentation.adapter.NoteAdapter
import com.inetkr.base.presentation.base.BaseFragment
import com.inetkr.base.presentation.base.entity.BaseError
import com.inetkr.base.utils.extensions.onLoadingMore
import com.inetkr.base.utils.extensions.onRefresh
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment :
    BaseFragment<HomeViewModel, FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val adapter: NoteAdapter by lazy {
        NoteAdapter(requireContext())
    }
    override val viewModel: HomeViewModel by viewModel()
    override fun backFromAddFragment() {

    }

    override fun initView() {
        binding.homeRecyclerView.apply {
            setListLayoutManager(LinearLayoutManager.VERTICAL)
            setAdapter(adapter)
            onRefresh {
                adapter.clear()
                adapter.showLoadingItem(true)
                binding.homeRecyclerView.enableRefresh(false)
                binding.homeRecyclerView.setEnableRefresh(false)
                viewModel.loadAlbums(true)
            }
            onLoadingMore {
                adapter.showLoadingItem(true)
                viewModel.loadAlbums(false)
            }
        }
        with(viewModel) {
            albumsReceivedLiveData.observe(this@HomeFragment, Observer {
                handleLoadMoreResponse(it)
            })
            adapter.showLoadingItem(true)
            binding.homeRecyclerView.setEnableRefresh(false)
            viewModel.loadAlbums(true)

        }
    }

    override fun handleValidateError(baseError: BaseError?) {
        super.handleValidateError(baseError)
        adapter.hideLoadingItem()
        binding.homeRecyclerView.setEnableRefresh(true)
    }

    override fun handleNetworkError(throwable: Throwable?, isShowDialog: Boolean) {
        super.handleNetworkError(throwable, isShowDialog)
        adapter.hideLoadingItem()
        binding.homeRecyclerView.setEnableRefresh(true)
    }

    override fun getListResponse(data: List<*>?, isRefresh: Boolean, canLoadmore: Boolean) {
        adapter.hideLoadingItem()
        binding.homeRecyclerView.setEnableRefresh(true)
        binding.homeRecyclerView.enableLoadMore(canLoadmore)
        if (data?.firstOrNull() is Note) {
            val result = data as List<Note>
            if (isRefresh) {
                binding.homeRecyclerView.refresh(result)
            } else {
                binding.homeRecyclerView.addItem(result)
            }

        }
        if (data.isNullOrEmpty()) {
            binding.homeRecyclerView.refresh(arrayListOf())
        }
    }

    override fun initData() {

    }

    override fun initListener() {
        adapter.onClickItem = {
            it?.let { note ->

            }
        }
    }

    override fun backPressed(): Boolean {

        return true
    }


    companion object {

        val FRAGMENT_NAME = HomeFragment::class.java.name
    }
}