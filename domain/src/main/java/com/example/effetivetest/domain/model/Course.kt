package com.example.effetivetest.domain.model

import com.example.effetivetest.domain.Constants

data class Course(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val cover: String = "",
    val updateDate: String = "",
    val price: String = "",
    val isFavorite: Boolean = false,
    val rating: Double = 0.0,
    val author: Author = Author(id = Constants.TEST_AUTHOR),
    val actualLink: String = "",
    val isActive: Boolean = false,
)
