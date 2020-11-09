package com.doga.microbloggingplatformsymphony.domain.repository

import com.doga.microbloggingplatformsymphony.domain.model.Author
import com.doga.microbloggingplatformsymphony.domain.model.Comment
import com.doga.microbloggingplatformsymphony.domain.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogWebService {
    @GET("/authors")
    fun getAuthors(@Query("_page") page: Int): Call<List<Author>>

    @GET("/posts")
    fun getPosts(@Query("_authorId") authorId: Int, @Query("_page") page: Int): Call<List<Post>>

    @GET("/comments")
    fun getComments(@Query("_postId") authorId: Int, @Query("_page") page: Int,
        @Query("_sort") sort: String, @Query("_order") order: String): Call<List<Comment>>
}