package com.inetkr.base.presentation.base.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inetkr.base.R
import com.inetkr.base.utils.extensions.inflate

abstract class EndlessLoadingRecyclerViewAdapter(context: Context, enableSelectedMode: Boolean) :
    RecyclerViewAdapter(context, enableSelectedMode) {

    private var loadingMoreListener: OnLoadingMoreListener? = null
    private var disableLoadMore = false
    protected var isLoading = false
    private var isEndOfList = false
    private var isCheckEndOfListLoadmore = false
    fun setLoadingMoreListener(loadingMoreListener: OnLoadingMoreListener?) {
        this.loadingMoreListener = loadingMoreListener
        enableLoadingMore(loadingMoreListener != null)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        var firstVisibleItemPosition = 0
                        var lastVisibleItemPosition = 0

                        if (disableLoadMore || isLoading) {
                            return
                        }
                        val layoutManager = recyclerView.layoutManager
                        if (layoutManager is LinearLayoutManager) {
                            firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                            lastVisibleItemPosition =
                                layoutManager.findLastCompletelyVisibleItemPosition()
                        } else if (layoutManager is GridLayoutManager) {
                            firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                            lastVisibleItemPosition =
                                layoutManager.findLastCompletelyVisibleItemPosition()
                        }

                        if (isEndOfList && !recyclerView.canScrollVertically(1)) {
                            isLoading = true
                            if (loadingMoreListener != null) {
                                loadingMoreListener!!.onLoadMore()
                            }
                        }
                        if (firstVisibleItemPosition > 0 && lastVisibleItemPosition == itemCount - 1) {
                            if (isCheckEndOfListLoadmore) {
                                isEndOfList = true
                            } else {
                                isLoading = true
                                if (loadingMoreListener != null) {
                                    loadingMoreListener!!.onLoadMore()
                                }
                            }
                        } else {
                            isEndOfList = false
                        }
                    }

                    else -> {
                    }
                }
            }
        })
    }

    fun setCheckEndOfListLoadMore(isCheck: Boolean){
        isCheckEndOfListLoadmore = isCheck
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    fun getIsLoading(): Boolean = this.isLoading

    fun enableLoadingMore(enable: Boolean) {
        this.disableLoadMore = !enable
    }

    fun getDisableLoadMore(): Boolean {
        return disableLoadMore
    }

    fun showLoadingItem(isScroll: Boolean) {
        isLoading = true
        addModel(null, VIEW_TYPE_LOADING, isScroll)
    }

    fun hideLoadingItem() {
        if (isLoading) {
            if(itemCount > 0 && getItemViewType(itemCount - 1) == VIEW_TYPE_LOADING) {
                removeModel(itemCount - 1)
            }
            isLoading = false
        }
    }

    override fun solvedOnCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val result: RecyclerView.ViewHolder
        when (viewType) {
            VIEW_TYPE_LOADING -> {
                result = LoadingViewHolder(parent.inflate(R.layout.layout_load_more))
            }

            else -> {
                result = initNormalViewHolder(parent)!!
            }
        }
        return result
    }

    override fun solvedOnBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        viewType: Int,
        position: Int
    ) {
        when (viewType) {
            VIEW_TYPE_LOADING -> {
//                bindLoadingViewHolder(viewHolder as LoadingViewHolder, position)
            }

            else -> {
                bindNormalViewHolder(viewHolder as RecyclerViewAdapter.NormalViewHolder, position)
            }
        }
    }

    interface OnLoadingMoreListener {
        fun onLoadMore()
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        const val VIEW_TYPE_LOADING = -1
    }
}
