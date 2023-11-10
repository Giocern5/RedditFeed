package com.example.tinderonsight.network

import com.example.tinderonsight.model.PostResp
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//https://www.reddit.com/r/aww/hot.json
interface HotPostAPI {
    //.json
    @GET("r/aww/hot.json")
    suspend fun getHotPosts(
        @Query("after") after: String?,
        @Query("limit") limit: Int,
        ): Response<PostResp>
}