package com.example.gc02.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Valuation(
    @PrimaryKey(autoGenerate = true) var valId: Long?,
    val points: Float = 0f,
    val comment: String = ""
) : Serializable