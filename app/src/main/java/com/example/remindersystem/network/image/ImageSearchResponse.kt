package com.example.remindersystem.network.image

data class ImageSearchResponse(
    val items: List<ImageSearchResponseItem>
)

data class ImageSearchResponseItem(
    val link: String
)