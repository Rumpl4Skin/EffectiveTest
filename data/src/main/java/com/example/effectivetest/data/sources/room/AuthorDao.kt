package com.example.effectivetest.data.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.effectivetest.data.model.room.AuthorEntity

@Dao
interface AuthorDao {
    @Query("SELECT * FROM authors WHERE id = :id LIMIT 1")
    suspend fun getAuthorById(id: Long): AuthorEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAuthor(author: AuthorEntity)
}