package com.materialsouk.meettvshow.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.materialsouk.meettvshow.R
import com.materialsouk.meettvshow.databinding.ItemContainerSliderImageBinding

class ImageSliderAdapter(private val sliderImages: Array<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.BindingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {

        val itemContainerSliderImageBinding: ItemContainerSliderImageBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_container_slider_image,
                parent,
                false
            )
        return BindingViewHolder(itemContainerSliderImageBinding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.itemBinding.imageURL = sliderImages[position]
    }

    override fun getItemCount(): Int {
        return sliderImages.size
    }


    class BindingViewHolder(val itemBinding: ItemContainerSliderImageBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

}
