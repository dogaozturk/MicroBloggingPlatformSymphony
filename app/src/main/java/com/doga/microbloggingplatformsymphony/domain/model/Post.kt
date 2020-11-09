package com.doga.microbloggingplatformsymphony.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post (
    @SerializedName("id")
    val id: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("authorId")
    val authorId : Int
) : Parcelable