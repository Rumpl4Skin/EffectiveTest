package com.example.effetivetest.domain.model



data class Course(
    val id: Long,
    val title: String,
    val description: String,
    val cover: String,
    val updateDate: String,
    val price: Double,
    val isFavorite: Boolean,
    val rating: Float,
    val author: String,
    val actualLink: String,
)
