package com.airasia.supportwheeloffate.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.airasia.supportwheeloffate.R
import com.airasia.supportwheeloffate.RemitApplication
import com.airasia.supportwheeloffate.core.BaseAdapter
import com.airasia.supportwheeloffate.data.local.HomeItem
import com.airasia.supportwheeloffate.databinding.AdapterHomeBinding

class HomeAdapter(private val clickCallback: ((HomeItem) -> Unit?)) :
    BaseAdapter<HomeItem>(object : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem == newItem
        }


    }) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val viewModel = HomeAdapterViewModel(parent.context.applicationContext as RemitApplication)
        val binding = DataBindingUtil.inflate<AdapterHomeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.adapter_home,
            parent,
            false
        )
        binding.viewModel = viewModel
        binding.root.setOnClickListener {
            binding.viewModel?.let { clickCallback?.invoke(it.homeData.get()!!) }
        }
        return binding
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        (binding as AdapterHomeBinding).viewModel?.setHomeData(getItem(position))
        binding.executePendingBindings()
    }

}