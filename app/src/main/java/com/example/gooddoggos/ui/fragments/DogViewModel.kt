package com.example.gooddoggos.ui.fragments

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gooddoggos.data.DogRepository
import com.example.gooddoggos.models.GoodDoggo
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

class DogViewModel @ViewModelInject constructor(private val repository: DogRepository): ViewModel() {

    fun getAllDogs() =
        repository.getAllDogs()

}