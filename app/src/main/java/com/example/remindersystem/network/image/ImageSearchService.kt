package com.example.remindersystem.network.image

class ImageSearchService(private val googleSearchApi: GoogleSearchApiService) {

    suspend fun searchForImageUrl(reminderName: String): String{
        val response = googleSearchApi.getImageFromGoogle(searchFor = reminderName)
        if (response.isSuccessful){
            val urls = response.body()?.items
            if(!urls.isNullOrEmpty()){
                return urls[0].link
            }
        }
        return "https://static.vecteezy.com/system/resources/previews/005/337/799/original/icon-image-not-found-free-vector.jpg"
    }

}