package com.faradhy.storyusersapp.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.faradhy.storyusersapp.data.UserRepository
import com.faradhy.storyusersapp.data.api.ApiConfig
import com.faradhy.storyusersapp.data.database.StoryDatabase
import com.faradhy.storyusersapp.data.preferences.UserPref
import com.faradhy.storyusersapp.data.preferences.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

val Context.dataStore by preferencesDataStore(name = "session")

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPref.getInstance(context.dataStore)
        val users = runBlocking { pref.getSession().first()}
        val apiService = ApiConfig.getApiService(users.token)
        val database = StoryDatabase.getDatabase(context)
        return UserRepository.getInstance(pref, apiService, database)
    }
}