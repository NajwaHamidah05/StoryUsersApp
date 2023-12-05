package com.faradhy.storyusersapp.view.signup

import androidx.lifecycle.ViewModel
import com.faradhy.storyusersapp.data.UserRepository

class SignupViewModel (private val userRepos: UserRepository) : ViewModel(){
    fun regist (name: String, email: String, password: String) = userRepos.registUpl(name, email, password)
}