package com.example.gc02.utils

class ArticleCheck private constructor() {

    var fail: Boolean = false
    var msg: String = ""
    var error: ArticleError = ArticleError.TitleError

    companion object{

        private val TAG = ArticleCheck::class.java.canonicalName

        private val checks = arrayOf(
            ArticleCheck().apply {
                fail = false
                msg = "Article are OK"
                error = ArticleError.Success
            },
            ArticleCheck().apply {
                fail = true
                msg = "Must have title"
                error = ArticleError.TitleError
            },
            ArticleCheck().apply {
                fail = true
                msg = "Must have description"
                error = ArticleError.DescError
            },
            ArticleCheck().apply {
                fail = true
                msg = "Must have price"
                error = ArticleError.PriceError
            },
            ArticleCheck().apply {
                fail = true
                msg = "Price can´t be less or equal to zero"
                error = ArticleError.PriceError
            },
            ArticleCheck().apply{
                fail = true
                msg = "Title must have between 6 and 25 characters"
                error = ArticleError.TitleError
            },
            ArticleCheck().apply {
                fail = true
                msg = "Description must have between 20 and 256 characters"
                error = ArticleError.DescError
            }
        )

        fun insert(title: String, desc: String, price: String): ArticleCheck {
            return if (title.isBlank()) checks[1]
            else if (desc.isBlank()) checks[2]
            else if (price.isBlank()) checks[3]
            else if (price.toInt() <= 0) checks[4]
            else if (title.length > 25 || title.length < 6) checks[5]
            else if (desc.length > 256 || desc.length < 20) checks[6]
            else checks[0]
        }
        fun modificar(title: String,desc: String, price: String): ArticleCheck {
            return if (title.isBlank()) checks[1]
            else if (desc.isBlank()) checks[2]
            else if (price.isBlank()) checks[3]
            else if (price.toInt() <= 0) checks[4]
            else if (title.length > 25 || title.length < 6) checks[5]
            else if (desc.length > 256 || desc.length < 20) checks[6]
            else checks[0]
        }
    }

    enum class ArticleError {
        TitleError, DescError, PriceError, Success
    }
}