package com.example.gc02.data.api

import com.google.gson.annotations.SerializedName

data class ShopDetails(
    @SerializedName("shop" ) var shop : Shop? = Shop()
)
