package com.example.remindersystem.network.holiday

import com.google.gson.annotations.SerializedName

data class HolidayResponse(
    val response: HolidayResponseData
)

data class HolidayResponseData(
    val holidays: List<Holiday>
)

data class Holiday(
    val name: String,
    @SerializedName("date") val date: DateInfo
)

data class DateInfo(
    @SerializedName("iso") val dateIso: String
)
