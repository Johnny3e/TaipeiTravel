package com.example.taipeitravel.ui.attraction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taipeitravel.data.model.AttractionImage
import com.example.taipeitravel.databinding.ItemImageBinding

class ImageSliderAdapter() : ListAdapter<AttractionImage, ImageSliderAdapter.ViewHolder>(
    diffCallback
) {

    inner class ViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.run {
            Glide.with(root).load(getItem(position).src).into(imageView)
        }
    }


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<AttractionImage>() {
            override fun areItemsTheSame(
                oldItem: AttractionImage,
                newItem: AttractionImage
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: AttractionImage,
                newItem: AttractionImage
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
