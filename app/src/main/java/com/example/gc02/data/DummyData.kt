package com.example.gc02.data

import com.example.gc02.model.Comentario
import com.example.gc02.model.User

var user1 = User(10,"Pedro","user@user.com","12345")
var user2 = User(20,"Juan","user2@user.com","12345")

val dummyComentarios: List<Comentario> = listOf(
    Comentario(
        1,
        user1.name,
        "Excelente"
    ),
    Comentario(
        2,
        user2.name,
        "Experiencia Horrible"
    ),
)