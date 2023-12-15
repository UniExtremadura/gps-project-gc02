package com.example.gc02.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserwithShops(
@Embedded
val user: User,
@Relation(
    parentColumn = "userId",
    entityColumn = "articleId",
    associateBy = Junction(UserShopCrossRef::class)
)
val shops: List<Article>
)
