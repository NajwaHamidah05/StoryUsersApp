package com.faradhy.storyusersapp.view.onBoarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.faradhy.storyusersapp.data.UserRepository
import com.faradhy.storyusersapp.data.preferences.UserModel

class OnboardingViewModel (private val repository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

}