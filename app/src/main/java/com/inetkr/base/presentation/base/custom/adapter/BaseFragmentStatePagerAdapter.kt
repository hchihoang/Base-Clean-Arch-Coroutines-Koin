package com.inetkr.base.presentation.base.custom.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

open class BaseFragmentStatePagerAdapter<T> : FragmentStateAdapter {
    private var context: Context
    private var itemsSource: List<T>

    constructor(
        context: Context,
        itemsSource: MutableList<T>,
        fragmentManager: FragmentManager?,
        lifecycle: Lifecycle?
    ) : super(
        fragmentManager!!, lifecycle!!
    ) {
        this.context = context
        this.itemsSource = itemsSource
    }

    constructor(
        context: Context,
        itemsSource: List<T>,
        fragment: Fragment?,
    ) : super(
        fragment!!
    ) {
        this.context = context
        this.itemsSource = itemsSource
    }

    override fun createFragment(position: Int): Fragment {
        return itemsSource[position] as Fragment
    }

    override fun getItemCount(): Int {
        return itemsSource.size
    }

    override fun getItemId(position: Int): Long {
        return itemsSource[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return !itemsSource.none { it.hashCode().toLong() == itemId }
    }
}