package com.loc.newsapp.data.remote

import com.loc.newsapp.data.remote.dto.NewsResponce
import com.loc.newsapp.ui.theme.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") source:String,
        @Query("apikey") apikey: String = Constants.API_KEY,
    ):NewsResponce

    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("sources") source:String,
        @Query("apikey") apikey: String = Constants.API_KEY,
    ):NewsResponce
}