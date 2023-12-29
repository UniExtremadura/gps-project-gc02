package com.example.gc02.api

import com.example.gc02.data.api.Categories
import com.example.gc02.data.api.Shop
import com.example.gc02.data.api.ShopDetails
import com.example.gc02.model.Article
import com.example.gc02.utils.SkipNetworkInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

private val service: ShopApi by lazy {
    val okHttpClient = OkHttpClient.Builder()
    //    .addInterceptor(SkipNetworkInterceptor())
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(ShopApi::class.java)
}

fun getNetworkService() = service

interface ShopApi {
    @GET("products")
    suspend fun getCat(
    ): List<Categories>
    @GET("products")
    suspend fun getCategories( ): List<Shop>

    @GET("products")
    suspend fun getCategories1( ): Response<List<Shop>>

    @GET("shop-details")
    suspend fun getShopDetail(
        @Query("q") id: Long?
    ): ShopDetails

   // @DELETE("products/{productId}")
   // fun deleteProduct(@Path("productId") productId: Long?): Call<Void>

    @PUT("products/{productId}")
    fun updateProduct(@Path("productId") productId: Long?, @Body shop: Shop): Call<Void>


}
class APIError(message: String, cause: Throwable?) : Throwable(message, cause)

interface APICallback {
    fun onCompleted(shops:List<Shop?>)
    fun onError(cause: Throwable)
}