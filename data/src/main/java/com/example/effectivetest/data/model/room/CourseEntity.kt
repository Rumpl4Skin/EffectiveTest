package com.example.effectivetest.data.model.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.effetivetest.domain.model.Author

@Entity(
    tableName = "courses",
    foreignKeys = [ForeignKey(
        entity = AuthorEntity::class,
        parentColumns = ["id"],
        childColumns = ["authorId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["authorId"])]
)
data class CourseEntity(
    @PrimaryKey val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val cover: String = "",
    val updateDate: String = "",
    val price: String = "",
    val isFavorite: Boolean = false,
    val rating: Double = 0.0,
    val authorId: Long = 0,
    val actualLink: String = "",
    val isActive: Boolean = false,
)