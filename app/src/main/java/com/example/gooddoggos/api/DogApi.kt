package com.example.gooddoggos.api

import com.example.gooddoggos.models.GoodDoggo
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val API_KEY = "765008e5-57c9-4f33-9b6e-91831b2ceb35"

interface DogApi {

    companion object {
        const val BASE_URL = "https://api.thedogapi.com/v1/"
        const val PAGE_SIZE = 10
    }

    @Headers("x-api-key: API_KEY")
    @GET("images/search")
    suspend fun getAllDogs(
        @Query("limit") limit:Int? = PAGE_SIZE,
        @Query("page") page :Int
    ): GoodDoggo
}