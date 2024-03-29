package com.faradhy.storyusersapp.data.api

import com.faradhy.storyusersapp.data.response.AddNewStoryResponse
import com.faradhy.storyusersapp.data.response.DetailStoryResponse
import com.faradhy.storyusersapp.data.response.GetAllStoryResponse
import com.faradhy.storyusersapp.data.response.LoginResponse
import com.faradhy.storyusersapp.data.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getAllStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): GetAllStoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStory(@Path("id") id: String
    ): DetailStoryResponse

    @GET("stories")
    suspend fun getStoriesByLocation(
        @Query("location") location:Int = 1
    ): GetAllStoryResponse


    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): AddNewStoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImageWithLocation(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat:RequestBody,
        @Part("lon") lon:RequestBody
    ): AddNewStoryResponse
}
