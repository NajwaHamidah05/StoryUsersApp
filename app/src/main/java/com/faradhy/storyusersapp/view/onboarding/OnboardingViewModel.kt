package com.faradhy.storyusersapp.view.onboarding
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.faradhy.storyusersapp.data.UserRepository
import com.faradhy.storyusersapp.data.preferences.UserModel

class OnboardingViewModel (private val userRepos: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepos.getSession().asLiveData()
    }

}