package com.example.gc02.model

import java.io.Serializable

data class Article(
    val title: String = "",
    val description: String = "",
    val price: String = "",
    var image: String = "" //IMAGEN PATH UNIDO AL API??
) : Serializable
