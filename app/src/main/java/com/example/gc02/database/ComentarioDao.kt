package com.example.gc02.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gc02.model.Comentario
@Dao
interface ComentarioDao {
    @Query("SELECT * FROM Comentario WHERE commentId = :id")
    suspend fun findById(id: Int): Comentario

    @Query("SELECT * FROM Comentario")
    suspend fun obtenerComentarios():List<Comentario>
    @Query("SELECT * FROM Comentario WHERE articleId = :articleId AND userId = :userId")
    suspend fun obtenerComentariosByArticleAndUser(articleId: Long, userId: Long):List<Comentario>

    @Query("SELECT * FROM Comentario")
    fun obtenerComentariosPrueba():List<Comentario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comentario: Comentario): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert1(comentario: Comentario): Long

    @Delete
    suspend fun delete(comentario: Comentario)
}