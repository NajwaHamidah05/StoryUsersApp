package com.faradhy.storyusersapp.view.story

import androidx.lifecycle.ViewModel
import com.faradhy.storyusersapp.data.UserRepository
import java.io.File

class StoryViewModel (private val userRepos: UserRepository): ViewModel() {

    fun uploadImage(file: File, description: String) = userRepos.imageUpl(file, description)
}