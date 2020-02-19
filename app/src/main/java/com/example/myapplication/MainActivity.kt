package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.product_list.*

class MainActivity : AppCompatActivity() {
    private var products : ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvProduct.layoutManager = GridLayoutManager(this, 2)

        var price = 12000
        for (i in 1..10) {
            price += i * 10
            val product = Product("Nama Produk $i", price)
            products.add(product)
        }

        rvProduct.adapter = ProductAdapter(products)
    }
}
