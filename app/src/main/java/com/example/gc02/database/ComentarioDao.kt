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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comentario: Comentario): Long

    @Delete
    suspend fun delete(comentario: Comentario)
}