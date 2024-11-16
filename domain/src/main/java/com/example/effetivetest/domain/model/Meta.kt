package com.example.effetivetest.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Meta(
    @SerialName("page")
    val page: Int,
    @SerialName("has_next")
    val hasNext: Boolean,
    @SerialName("has_previous")
    var hasPrevious: Boolean
)