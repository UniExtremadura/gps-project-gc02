package com.example.gc02.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class Comentario(
    @PrimaryKey(autoGenerate = true) val commentId : Long?,
    val autor: String,
    val comentario: String
):Serializable
