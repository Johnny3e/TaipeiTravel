package com.example.taipeitravel

import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taipeitravel.data.repo.TravelRepoImpl
import com.example.taipeitravel.data.src.HttpService

class AppContainer {

    val travelRepo by lazy { TravelRepoImpl(HttpService.create()) }
}