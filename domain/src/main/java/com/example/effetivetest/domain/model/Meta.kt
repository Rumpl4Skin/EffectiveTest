package com.example.effetivetest.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class Meta(
    val page: Int,
    val hasNext: Boolean,
    var hasPrevious: Boolean
)