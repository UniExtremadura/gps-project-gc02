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
            }

        )

        fun insert(title: String, desc: String, price: String): ArticleCheck {
            return if (title.isBlank()) checks[1]
            else if (desc.isBlank()) checks[2]
            else if (price.isBlank()) checks[3]
            else checks[0]
        }
    }

    enum class ArticleError {
        TitleError, DescError, PriceError, Success
    }
}