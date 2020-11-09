package com.doga.microbloggingplatformsymphony.domain.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Comment (
    @SerializedName("id")
    val id: Int,
    @SerializedName("date")
    val date: Date,
    @SerializedName("body")
    val body: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("avatarUrl")
    val avatarUrl: String,
    @SerializedName("postId")
    val postId : Int
)