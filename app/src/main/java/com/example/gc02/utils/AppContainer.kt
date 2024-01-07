package com.example.gc02.utils

import android.content.Context
import com.example.gc02.api.getNetworkService
import com.example.gc02.data.Repository
import com.example.gc02.database.BaseDatos

class AppContainer(context: Context?) {

    private val networkService = getNetworkService()
    private val db = BaseDatos.getInstance(context!!)
    val repository = Repository(db!!,getNetworkService())

}