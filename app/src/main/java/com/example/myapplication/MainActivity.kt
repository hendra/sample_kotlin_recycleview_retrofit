package com.example.myapplication

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private var productList : ArrayList<Product> = ArrayList()

    private val TAG = javaClass.simpleName
    private var productAdapter by Delegates.notNull<ProductAdapter>()
    private var isLoading by Delegates.notNull<Boolean>()
    private var page by Delegates.notNull<Int>()
    private var totalPage by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        page = 1
        totalPage = 0

        val apiInterface: ProductApi = ApiClient.getClient().create(ProductApi::class.java)

        doLoadData(this, apiInterface)
        initListener(apiInterface)

        //** Set the colors of the Pull To Refresh View
        swipeContainer.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        swipeContainer.setColorSchemeColors(Color.WHITE)

        swipeContainer.setOnRefreshListener {
            productList.clear()
            doLoadData(this, apiInterface)
            swipeContainer.isRefreshing = false
        }
    }

    fun doLoadData(context: Context, apiInterface: ProductApi) {
        Log.d(TAG, "page: $page")
        showLoading(true)

        apiInterface.getProducts(31, page)
            .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { product: List<Product> ->
                        productList.addAll(product)
                        if (page == 1) {
                            productAdapter = ProductAdapter(this@MainActivity, productList)
                            rvProduct.layoutManager = GridLayoutManager(this@MainActivity, 2)
                            rvProduct.adapter = productAdapter
                        } else {
                            productAdapter.refreshAdapter(productList)
                        }
                        totalPage = 2
                    },
                    { t: Throwable ->
                        Toast.makeText(context, "Request to server failed", Toast.LENGTH_SHORT).show()
                        t.printStackTrace()
                    },
                    {
                        hideLoading()
                    }
                )
    }

    private fun initListener(apiInterface: ProductApi) {
        rvProduct.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager?.itemCount
                val lastVisiblePosition = linearLayoutManager?.findLastCompletelyVisibleItemPosition()
                val isLastPosition = countItem.minus(1) == lastVisiblePosition
                Log.d(TAG, "countItem: $countItem")
                Log.d(TAG, "lastVisiblePosition: $lastVisiblePosition")
                Log.d(TAG, "isLastPosition: $isLastPosition")
                if (!isLoading && isLastPosition && page < totalPage) {
                    showLoading(true)
                    page = page.let { it.plus(1) }
                    doLoadData(this@MainActivity, apiInterface)
                }
            }
        })
    }

    private fun showLoading(isRefresh: Boolean) {
        isLoading = true
        progress_bar_horizontal_activity_main.visibility = View.VISIBLE
        rvProduct.visibility.let {
            if (isRefresh) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun hideLoading() {
        isLoading = false
        progress_bar_horizontal_activity_main.visibility = View.GONE
        rvProduct.visibility = View.VISIBLE
    }
}
