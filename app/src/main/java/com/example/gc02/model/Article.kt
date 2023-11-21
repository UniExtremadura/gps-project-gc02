package com.example.gc02.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class Article(
    @PrimaryKey(autoGenerate = true) var articleId: Long?,
    val title: String = "",
    val description: String = "",
    val price: String = ""
    //var image: String = "" //IMAGEN PATH UNIDO AL API??
) : Serializable
