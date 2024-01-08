package com.example.gc02.data

import com.example.gc02.data.api.Shop
import com.example.gc02.model.Article


fun Shop.toShop() = Article(
    articleId = id,
    title = title ?: "",
    description = description ?: "",
    price = price ?: 0.0,
    image = image ?: "",
    isFavorite = false,
    userId = 999
)