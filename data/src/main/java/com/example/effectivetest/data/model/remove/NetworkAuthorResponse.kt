package com.example.effectivetest.data.model.remove

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAuthorResponse(
    @SerialName("meta")
    val meta: NetworkMeta,
    @SerialName("users")
    val authors: List<NetworkAuthor>,
)