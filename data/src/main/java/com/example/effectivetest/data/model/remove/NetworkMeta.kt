package com.example.effectivetest.data.model.remove

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMeta(
    @SerialName("page")
    val page: Int,
    @SerialName("has_next")
    val hasNext: Boolean,
    @SerialName("has_previous")
    var hasPrevious: Boolean
)