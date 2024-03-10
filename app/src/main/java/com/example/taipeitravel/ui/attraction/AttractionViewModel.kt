package com.example.taipeitravel.ui.attraction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taipeitravel.App
import com.example.taipeitravel.data.model.AttractionImage
import com.example.taipeitravel.data.repo.TravelRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AttractionViewModel(
    private val travelRepo: TravelRepo
) : ViewModel() {

    data class UiState(
        val images: List<AttractionImage> = emptyList(),
        val openTime: String = "",
        val address: String = "",
        val tel: String = "",
        val officialSite: String = "",
        val introduction: String = "",
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun getAttraction(id: Int) {
        travelRepo.getAttractionById(id).run {
            _uiState.update {
                it.copy(
                    images = images,
                    openTime = openTime,
                    address = address,
                    tel = tel,
                    officialSite = officialSite,
                    introduction = introduction
                )
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val travelRepo = (this[APPLICATION_KEY] as App).appContainer.travelRepo
                AttractionViewModel(travelRepo)
            }
        }
    }
}