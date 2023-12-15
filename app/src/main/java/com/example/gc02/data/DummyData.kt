package com.example.gc02.data

import com.example.gc02.model.Article
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


val dummyArticulos: List<Article> = listOf(
    Article(null, "Laptop", "Laptop de última generación", 1299.99,"Ordenadores",""),
    Article(null, "Teléfono", "Teléfono inteligente de gama alta", 799.99, "Tecnología", ""),
    Article(null, "Auriculares", "Auriculares inalámbricos con cancelación de ruido", 149.99, "Tecnología", ""),
    Article(null, "Smartwatch", "Smartwatch con seguimiento de actividad", 199.99, "Tecnología", ""),
    Article(null, "Tablet", "Tablet con pantalla retina", 499.99, "Tecnología", ""),
    Article(null, "Cámara", "Cámara digital de alta resolución", 599.99, "Tecnología", ""),
    Article(null, "Altavoces", "Altavoces Bluetooth de calidad premium", 129.99, "Tecnología", ""),
    Article(null, "Impresora", "Impresora láser todo en uno", 299.99, "Tecnología", "")/*,
    Article(9, "Teclado y ratón", "Combo de teclado y ratón inalámbricos", "79.99"),
    Article(10, "Monitor", "Monitor IPS de 27 pulgadas", "349.99"),
    Article(11, "Mochila", "Mochila resistente para portátiles", "49.99"),
    Article(12, "Silla de oficina", "Silla ergonómica con soporte lumbar", "149.99"),
    Article(13, "Funda para laptop", "Funda acolchada para laptops de 15 pulgadas", "29.99"),
    Article(14, "Ratón gaming", "Ratón óptico para juegos con luces LED", "59.99"),
    Article(15, "Tarjeta de memoria", "Tarjeta microSD de 128 GB clase 10", "39.99"),
    Article(16, "Disco duro externo", "Disco duro externo de 1 TB USB 3.0", "79.99"),
    Article(17, "Cargador portátil", "Cargador portátil de 20000 mAh", "49.99"),
    Article(18, "Cable USB-C", "Cable USB-C de carga rápida", "9.99"),
    Article(19, "Hub USB", "Hub USB de 4 puertos con adaptador de corriente", "19.99"),
    Article(20, "Lámpara de escritorio", "Lámpara LED ajustable para escritorio", "34.99")
*/
)