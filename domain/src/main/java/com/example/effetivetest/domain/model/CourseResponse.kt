package com.example.effetivetest.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseResponse(
    @SerialName("meta")
    val meta: Meta,
    @SerialName("courses")
    val courses: List<Course>,
    @SerialName("enrollments")
    val enrollments: List<String>?
)
