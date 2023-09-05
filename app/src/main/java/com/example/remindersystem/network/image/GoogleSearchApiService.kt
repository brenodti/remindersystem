package com.example.remindersystem.network.image

import com.example.remindersystem.BuildConfig
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.googleapis.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface GoogleSearchApiService{

    @GET("customsearch/v1")
    suspend fun getImageFromGoogle(
        @Query("key") apiKey: String = BuildConfig.GOOGLE_API_KEY,
        @Query("cx") searchEngineId: String = BuildConfig.GOOGLE_SEARCH_ENGINE_ID,
        @Query("searchType") searchType: String = "image",
        @Query("imgSize") imageSize: String = "large",
        @Query("imgType") imageType: String = "photo",
        @Query("num") quantity: Int = 1,
        @Query("fileType") fileType: String = "png",
        @Query("q") searchFor: String
    ): Response<ImageSearchResponse>

    object GoogleSearchApi {
        val retrofitService: GoogleSearchApiService by lazy {
            retrofit.create(GoogleSearchApiService::class.java)
        }
    }
}