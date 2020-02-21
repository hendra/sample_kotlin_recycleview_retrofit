package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.afollestad.vvalidator.form
import kotlinx.android.synthetic.main.product_new.*

class ProductNew : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_new)

        //actionbar
        val actionbar = supportActionBar

        //set actionbar title
        actionbar!!.title = "New Product"

        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        // Initializing a String Array
        val colors = arrayOf("Red","Green","Blue","Yellow","Black","Crimson","Orange")

        val colorAdapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, colors)
        spColor.adapter = colorAdapter

        // Initializing a String Array
        val sizes = arrayOf("XXS","XS","S","M","L","XL","XXL", "XXXL")

        val sizeAdapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, sizes)
        spSize.adapter = sizeAdapter

        btnSubmit.setOnClickListener {
            form {
                input(etProductName) {
                    isNotEmpty()
                }
                input(etProductDescription) {
                    isNotEmpty()
                }
                input(etPrice) {
                    isNotEmpty()
                }
                input(etInStock) {
                    isNotEmpty()
                }
                submitWith(btnSubmit) { result ->
                    // this block is only called if form is valid.
                    // do something with a valid form state.

                    val apiInterface: ProductApi = ApiClient.getClient().create(ProductApi::class.java)

                    apiInterface.createProduct(31,
                                                etProductName.text,
                                                etProductDescription.text,
                                                spColor.selectedItem,
                                                spSize.selectedItem,
                                                etPrice.text,
                                                etInStock.text)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
