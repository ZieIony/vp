package com.vp.list.service

import com.vp.list.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/")
    fun search(@Query("s") query: String?, @Query("page") page: Int): Call<SearchResponse>
}