package com.example.taipeitravel.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taipeitravel.data.model.Language
import com.example.taipeitravel.databinding.ItemTextBinding


class LanguageAdapter(val selectedLanguage: Language, val onItemClick: (Language) -> Unit) :
    ListAdapter<Language, LanguageAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(getItem(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.run {
            val currentItem = getItem(position)
            tvLanguage.text = currentItem.text
            if (currentItem == selectedLanguage) {
                tvLanguage.isSelected = true
            } else {
                tvLanguage.isSelected = false
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Language>() {
            override fun areItemsTheSame(oldItem: Language, newItem: Language): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Language, newItem: Language): Boolean {
                return oldItem == newItem
            }
        }
    }
}