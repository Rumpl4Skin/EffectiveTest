package com.example.effetivetest.domain.model



data class Course(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val cover: String = "",
    val updateDate: String= "",
    val price: Double = 0.0,
    val isFavorite: Boolean = false,
    val rating: Float =0f,
    val author: Author =Author(id=0),
    val actualLink: String= "",
)
