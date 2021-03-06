package com.example.myapplication

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.product_list.view.*


class ProductAdapter(val products: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.product_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(products.get(position))
    }

    fun refreshAdapter(productList: ArrayList<Product>) {
        this.products.addAll(productList)
        notifyItemRangeChanged(0, this.products.size)
    }

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var view : View = itemView
        private lateinit var product : Product

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View) {
            val intent = Intent(view.context, ProductDetail::class.java)
            view.context.startActivity(intent)
        }

        fun bindData(product: Product) {
            this.product = product
            val productImageUrl  = product.images!!.first().imageUrl

            Glide.with(view.context)
                .load(productImageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.noimage)
                .into(view.productImage)
            view.productName.setText(product.name)
            view.productPrice.setText(product.price.toString())
        }
    }
}