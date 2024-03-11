package com.example.taipeitravel.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taipeitravel.App
import com.example.taipeitravel.data.model.Attraction
import com.example.taipeitravel.data.model.Language
import com.example.taipeitravel.data.model.News
import com.example.taipeitravel.data.repo.TravelRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val travelRepo: TravelRepo
) : ViewModel() {

    data class UiState(
        val news1: News? = null,
        val news2: News? = null,
        val news3: News? = null,
        val attractions: List<Attraction> = emptyList()
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun getData(language: Language) = viewModelScope.launch {
        val newsDef = async { travelRepo.getNews(language.tag) }
        val attractionsDef = async { travelRepo.getAttractions(language.tag) }
        val news = newsDef.await()
        val attractions = attractionsDef.await()
        _uiState.update {
            it.copy(
                news.getOrNull(0),
                news.getOrNull(1),
                news.getOrNull(2),
                attractions
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