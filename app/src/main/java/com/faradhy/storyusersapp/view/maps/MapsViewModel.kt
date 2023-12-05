package com.faradhy.storyusersapp.view.maps

import androidx.lifecycle.ViewModel
import com.faradhy.storyusersapp.data.UserRepository

class MapsViewModel(private val repository: UserRepository):ViewModel() {
    fun getStoriesByLocation() = repository.getStoriesByLocation()
}