package com.example.effectivetest.data.model.remove

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCourseResponse (
    @SerialName("meta")
    val meta: NetworkMeta,
    @SerialName("courses")
    val courses: List<NetworkCourse>,
)