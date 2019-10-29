package com.airasia.supportwheeloffate.ui.nav.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.airasia.supportwheeloffate.R
import com.airasia.supportwheeloffate.RemitApplication
import com.airasia.supportwheeloffate.core.BaseAdapter
import com.airasia.supportwheeloffate.data.local.NavigationItem

class NavigationDrawerListAdapter(private val clickCallback: ((NavigationItem) -> Unit)?) :
    BaseAdapter<NavigationItem>(object : DiffUtil.ItemCallback<NavigationItem>() {
        override fun areItemsTheSame(oldItem: NavigationItem, newItem: NavigationItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: NavigationItem, newItem: NavigationItem): Boolean {
            return oldItem == newItem
        }


    }) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val viewModel = NavigationDrawerItemViewModel(parent.context.applicationContext as RemitApplication)

        val binding = DataBindingUtil.inflate<com.airasia.supportwheeloffate.databinding.RowNavigationBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_navigation,
            parent,
            false
        )
        binding.viewModel = viewModel
        binding.root.setOnClickListener {
            binding.viewModel?.let { clickCallback?.invoke(it.navigationItem) }
        }
        return binding
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        (binding as com.airasia.supportwheeloffate.databinding.RowNavigationBinding).viewModel?.setItem(getItem(position))
        binding.executePendingBindings()
    }
}
