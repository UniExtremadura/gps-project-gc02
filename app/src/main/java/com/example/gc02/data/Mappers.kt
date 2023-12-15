package com.example.gc02.data

import com.example.gc02.data.api.Categories
import com.example.gc02.data.api.Shop
import com.example.gc02.model.Article
import com.example.gc02.model.Category


fun Shop.toShop() = Article(
    articleId = null,
    title = title ?: "",
    description = description ?: "",
    price = price ?: 0.0,
    category = category ?: "",
    image = image ?: ""
)

fun Categories.toCategories() = Category(
    name = name ?: ""
)