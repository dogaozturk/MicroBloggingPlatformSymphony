package com.doga.microbloggingplatformsymphony.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Author(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("avatarUrl")
    val avatarUrl: String,
    @SerializedName("address")
    val address : Address
) : Parcelable {
    @Parcelize
    data class Address(
        @SerializedName("latitude")
        val latitude: String,
        @SerializedName("longitude")
        val longitude: String
    ) : Parcelable
}