package com.example.myapplication

import io.reactivex.Observable
import retrofit2.Callback
import retrofit2.http.*

interface ProductApi {
    @GET("stores/{id}/products")
    fun getProducts(@Path("id") id: Int, @Query("page") page: Int = 1): Observable<List<Product>>

    @FormUrlEncoded
    @POST("stores/{id}/products")
    fun createProduct(@Path("id") id: Int,
                      @Field("name") name: String?,
                      @Field("description") name: String?,
                      @Field("color") name: String?,
                      @Field("size") name: String?,
                      @Field("instock") name: Int?,
                      @Field("price") name: Int?): Callback<Product>
}