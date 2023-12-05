package com.faradhy.storyusersapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.faradhy.storyusersapp.data.UserRepository
import com.faradhy.storyusersapp.data.preferences.UserModel
import com.faradhy.storyusersapp.data.response.RowItemStory
import kotlinx.coroutines.launch

class MainViewModel (private val userRepos: UserRepository) : ViewModel() {


    val story: LiveData<PagingData<RowItemStory>> =
        userRepos.getAllStory().cachedIn(viewModelScope)

    fun getSession(): LiveData<UserModel> {

        return userRepos.getSession().asLiveData()
    }

    fun logout (){
        viewModelScope.launch {
            userRepos.logOut()
        }
    }


}