package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET

interface ProductApi {
    @GET("stores/1/products")
    fun getProducts(): Call<List<Product>>
}