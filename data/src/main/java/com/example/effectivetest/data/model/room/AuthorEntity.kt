package com.example.effectivetest.data.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class AuthorEntity (
    @PrimaryKey val id: Long,
    val fullName:String = "",
    val photo: String = ""
)