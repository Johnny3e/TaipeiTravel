package com.example.taipeitravel.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taipeitravel.App
import com.example.taipeitravel.data.model.News
import com.example.taipeitravel.data.repo.TravelRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val travelRepo: TravelRepo
) : ViewModel() {

    data class UiState(
        val news1: News? = null,
        val news2: News? = null,
        val news3: News? = null,
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getNews()
    }

    fun getNews() = viewModelScope.launch {
        val list = travelRepo.getNews("zh-tw")
        _uiState.update {
            it.copy(
                list.getOrNull(0),
                list.getOrNull(1),
                list.getOrNull(2),
            )
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val travelRepo = (this[APPLICATION_KEY] as App).appContainer.travelRepo
                MainViewModel(travelRepo)
            }
        }
    }
}