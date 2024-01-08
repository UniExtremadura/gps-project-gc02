package com.example.gc02.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class Article(
    @PrimaryKey(autoGenerate = true) var articleId: Long?,
    var title: String = "",
    var description: String = "",
    var price: Double = 0.0,
    val image: String? = "",
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean,
    val userId: Long? = null
) : Serializable
