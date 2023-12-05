package com.faradhy.storyusersapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.faradhy.storyusersapp.data.api.ApiService
import com.faradhy.storyusersapp.data.database.StoryDatabase
import com.faradhy.storyusersapp.data.paging.StoryRemoteMediator
import com.faradhy.storyusersapp.data.preferences.UserModel
import com.faradhy.storyusersapp.data.preferences.UserPref
import com.faradhy.storyusersapp.data.response.AddNewStoryResponse
import com.faradhy.storyusersapp.data.response.DetailStoryResponse
import com.faradhy.storyusersapp.data.response.GetAllStoryResponse
import com.faradhy.storyusersapp.data.response.LoginResponse
import com.faradhy.storyusersapp.data.response.RegisterResponse
import com.faradhy.storyusersapp.data.response.RowItemStory
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UserRepository private constructor(
    private val userPref: UserPref,
    private val apiService: ApiService,
    private val database: StoryDatabase
) {

    suspend fun saveSession(user: UserModel) {
        userPref.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPref.getSession()
    }

    suspend fun logOut (){
        return userPref.logout()
    }

    fun registUpl(name: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.register(name, email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(errorResponse.message?.let { ResultState.Error(it) })
        }

    }

    fun loginUpl(email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.userLogin(email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(errorResponse.message?.let { ResultState.Error(it) })
        }
    }

    fun imageUpl(imageFile: File, description: String) = liveData {
        emit(ResultState.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.uploadImage(multipartBody, requestBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, AddNewStoryResponse::class.java)
            emit(errorResponse.message.let { ResultState.Error(it) })
        }
    }


    fun getAllStory() : LiveData<PagingData<RowItemStory>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(database, apiService),
            pagingSourceFactory = {
                database.storyDao().getAllStory()
            }
        ).liveData
    }

    fun getStoriesByLocation() = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getStoriesByLocation()
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, GetAllStoryResponse::class.java)
            emit(errorResponse.message?.let { ResultState.Error(it) })
        }
    }

    fun getDetailStory(id:String) = liveData {
        try {
            val successResponse = apiService.getDetailStory(id)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, DetailStoryResponse::class.java)
            emit(errorResponse.message?.let { ResultState.Error(it) })
        }
    }

    fun uploadImageWithLocation(imageFile: File, description: String,lat:Double,lon:Double) = liveData {
        emit(ResultState.Loading)
        val lonRequestBody = lon.toString().toRequestBody("text/plain".toMediaType())
        val latrequestBody = lat.toString().toRequestBody("text/plain".toMediaType())
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile,
        )
        try {
            val successResponse = apiService.uploadImageWithLocation(multipartBody, requestBody,latrequestBody,lonRequestBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, AddNewStoryResponse::class.java)
            emit(errorResponse.message.let { ResultState.Error(it) })
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(userPref: UserPref, apiService: ApiService, database: StoryDatabase
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPref, apiService, database)
            }.also { instance = it }
    }
}

