package com.example.gc02.model

import androidx.room.PrimaryKey
import java.io.Serializable

data class Comentario(
    @PrimaryKey(autoGenerate = true) val commentId : Long?,
    val autor: String,
    val comentario: String
):Serializable
