package com.example.gc02.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gc02.model.Article
//import es.unex.giiis.asee.tiviclone.data.model.UserShowCrossRef
//import es.unex.giiis.asee.tiviclone.data.model.UserWithShows

@Dao
interface ArticleDao {

    @Query("SELECT * FROM Article WHERE articleId = :id")
    suspend fun findById(id: Int): Article

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Delete
    suspend fun delete(article: Article)
}