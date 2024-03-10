package com.example.taipeitravel.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taipeitravel.data.model.Attraction
import com.example.taipeitravel.databinding.ItemAttractionBinding


class AttractionAdapter : ListAdapter<Attraction, AttractionAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(val binding: ItemAttractionBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAttractionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val attraction = getItem(position)
            holder.binding.run {
                attraction.images.firstOrNull()?.let { Glide.with(ivPic).load(it.src).into(ivPic) }
                tvTitle.text = attraction.name
                content.text = attraction.introduction
            }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Attraction>() {
            override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
                return oldItem == newItem
            }
        }
    }
}