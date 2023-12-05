package com.faradhy.storyusersapp.di

import android.content.Context
import com.faradhy.storyusersapp.data.UserRepository
import com.faradhy.storyusersapp.data.preferences.UserPreference
import com.faradhy.storyusersapp.data.preferences.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}