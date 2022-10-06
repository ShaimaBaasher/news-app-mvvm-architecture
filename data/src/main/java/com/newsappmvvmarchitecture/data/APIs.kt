package com.newsappmvvmarchitecture.data

import com.shaima.data.utils.WrappedNewsResponse
import retrofit2.http.GET

import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query


interface APIs {

    @GET("viewed/{period}.json")
    suspend fun getMostViewedNews(
        @Path("period") period : String,
        @Query("api-key") apiKey: String):
            Response<WrappedNewsResponse>

}