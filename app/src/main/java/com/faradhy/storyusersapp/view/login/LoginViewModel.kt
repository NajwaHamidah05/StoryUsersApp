package com.faradhy.storyusersapp.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faradhy.storyusersapp.data.UserRepository
import com.faradhy.storyusersapp.data.preferences.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepos: UserRepository) : ViewModel() {

    fun login (email : String, password : String) = userRepos.loginUpl(email, password)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepos.saveSession(user)
        }
    }
}