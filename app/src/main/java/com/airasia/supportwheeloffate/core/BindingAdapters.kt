package com.airasia.supportwheeloffate.core

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airasia.supportwheeloffate.BuildConfig
import com.airasia.supportwheeloffate.utils.extensions.setHintSpinnerAdapter
import com.squareup.picasso.Picasso
import fr.ganfra.materialspinner.MaterialSpinner


object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:set_list")
    fun setList(recyclerView: RecyclerView?, list: List<Nothing>?) {
        val adapter: BaseAdapter<*>? = recyclerView?.adapter as BaseAdapter<*>?
        list?.let {
            adapter?.submitList(null)
            adapter?.submitList(list)
        }
    }

    @JvmStatic
    @BindingAdapter("app:set_list")
    fun setList(spinner: MaterialSpinner, list: List<String>?) {
        list?.let {
            spinner.setHintSpinnerAdapter(list)
        }
    }

    @JvmStatic
    @BindingAdapter("app:setImgResource")
    fun setImgResource(imageView: ImageView?, resourceId: Int?) {
        if (resourceId != null)
            imageView?.setImageResource(resourceId)
        else imageView?.setImageResource(0)
    }

    @JvmStatic
    @BindingAdapter("app:setImgResource")
    fun setImgResource(imageView: ImageView?, imageUrl: String?) {
        //val imageUrl = bankDetail.iconurl
        if (imageUrl?.isNullOrEmpty() == true)
            return
        val picasso = Picasso.Builder(imageView?.context!!)
            .listener { _, _, e ->
                if (BuildConfig.DEBUG)
                    e.printStackTrace()
            }
            .build()
        picasso.load(imageUrl)
            .into(imageView)

        Log.v("TAG", "testing image: $imageUrl")
    }

}




