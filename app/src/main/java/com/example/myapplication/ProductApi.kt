package com.example.myapplication

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("stores/{id}/products")
    fun getProducts(@Path("id") id: Int, @Query("page") page: Int = 1): Observable<List<Product>>
}