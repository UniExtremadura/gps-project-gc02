package com.example.gc02.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["userId", "articleId"],
    foreignKeys = [
        ForeignKey(
            entity = Article::class,
            parentColumns = ["articleId"],
            childColumns = ["articleId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserShopCrossRef(
    val userId: Long,
    val articleId: Long
)
