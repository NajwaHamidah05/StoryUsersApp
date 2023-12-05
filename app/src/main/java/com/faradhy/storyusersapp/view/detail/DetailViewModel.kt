package com.faradhy.storyusersapp.view.detail

import androidx.lifecycle.ViewModel
import com.faradhy.storyusersapp.data.UserRepository

class DetailViewModel(private val repository: UserRepository):ViewModel() {

    fun getDetailStory (id:String) = repository.getDetailStory(id)
}