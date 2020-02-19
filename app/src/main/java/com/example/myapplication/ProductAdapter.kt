package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var view : View = itemView
        private lateinit var product : Product

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View) {
            Toast.makeText(view.context, "${product.name} Diklik", Toast.LENGTH_SHORT).show()
        }

        fun bindData(product: Product) {
            this.product = product
            view.productImage.setImageResource(R.drawable.product)
            view.productName.setText(product.name)
            view.productPrice.setText(product.price.toString())
        }
    }
}