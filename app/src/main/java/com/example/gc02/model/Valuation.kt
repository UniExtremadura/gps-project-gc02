package com.example.gc02.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Valuation(
    @PrimaryKey(autoGenerate = true) var valId: Long?,
    val points: Int = 0,
    val comment: String = "",
    val userId: Long? = null
) : Serializable