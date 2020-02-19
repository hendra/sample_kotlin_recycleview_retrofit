package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val SERVER_URL = "http://10.0.2.2:3000/api/"
    var products : ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiInterface : ProductApi = ApiClient.getClient().create(ProductApi::class.java)

        rvProduct.layoutManager = GridLayoutManager(this,2)

        getProducts(apiInterface)
    }

    fun getProducts(apiInterface: ProductApi) {
        apiInterface.getProducts()
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    response.body()?.forEach {
                        products.add(it)
                    }

                    rvProduct.adapter = ProductAdapter(products)
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) = t.printStackTrace()
            })
    }
}
