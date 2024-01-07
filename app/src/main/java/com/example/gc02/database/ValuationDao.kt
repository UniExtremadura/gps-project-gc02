package com.example.gc02.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.gc02.model.Valuation

@Dao
interface ValuationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(valuation: Valuation): Long

    @Transaction
    @Query("SELECT * FROM Valuation")
    suspend fun obtenerValoraciones(): List<Valuation>

    @Query("SELECT * FROM Valuation WHERE valId = :id")
    suspend fun findById(id: Int): Valuation

    @Transaction
    @Query("SELECT * FROM Valuation WHERE userId=:userId")
    suspend fun getAllByUser(userId: Long?): List<Valuation>

    @Transaction
    @Query("SELECT * FROM Valuation WHERE userId=:userId")
    fun getValuationsByUser(userId: Long): LiveData<List<Valuation>>


    @Delete
    suspend fun delete(valuation: Valuation)
}