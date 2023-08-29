package com.example.remindersystem.network.holiday

import com.example.remindersystem.BuildConfig
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://calendarific.com/api/v2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface CalendarificApiService {

    @GET("holidays")
    suspend fun getHolidays(
        @Query("api_key") apiKey: String = BuildConfig.CALENDARIFIC_API_KEY,
        @Query("country") country: String = "BR",
        @Query("year") year: Int = 2023,
        @Query("month") month: Int = 10,
        @Query("type") type: String = "national"
    ): Response<HolidayResponse>


    object CalendarificApi {
        val retrofitService: CalendarificApiService by lazy {
            retrofit.create(CalendarificApiService::class.java)
        }
    }
}