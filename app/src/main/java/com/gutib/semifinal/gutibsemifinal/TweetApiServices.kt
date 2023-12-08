package com.gutib.semifinal.gutibsemifinal

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TweetApiServices {
    @POST("tweet/gutib/")
    fun createTweet(@Body tweet: Tweet): Call<Void>

    @PUT("tweet/gutib/{tweetId}")
    fun updateTweet(@Path("tweetId") id: String, @Body tweet: Tweet): Call<Tweet>
}