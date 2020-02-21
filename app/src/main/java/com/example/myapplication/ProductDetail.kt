package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle

class ProductDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_detail)

        //actionbar
        val actionbar = supportActionBar

        //set actionbar title
        actionbar!!.title = "Product Detail"

        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}