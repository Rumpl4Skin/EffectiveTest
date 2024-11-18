package com.example.effectivetest.data.model.remove

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAuthor (
    @SerialName("id")
    val id:Long,
    @SerialName("full_name")
    val fullName:String,
    @SerialName("avatar")
    val photo: String
)