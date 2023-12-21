package com.example.gc02.UnitTests

import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gc02.api.ShopApi

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import kotlinx.coroutines.runBlocking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@LargeTest
@RunWith(AndroidJUnit4::class)
class ShopTest {

    private lateinit var apiService: ShopApi

    @Before
    fun setUp() {
        // Configurar Retrofit y crear la instancia de ApiService
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/") // Reemplaza con tu URL de prueba
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ShopApi::class.java)
    }

    @Test
    fun testAllArticle() = runBlocking {
        val result = apiService.getCategories1()
        // Aquí puedes realizar las aserciones según tus expectativas
        assert(result.isSuccessful)
        assert(result.body()!=null)
    }
}