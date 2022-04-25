package ru.company.listofgoods.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import ru.company.listofgoods.R
import ru.company.listofgoods.api.GoodsApi
import ru.company.listofgoods.databinding.ActivityMainBinding
import ru.mypackage.myweatherapp.adapter.GoodsAdapter
import ru.mypackage.nmedia.error.ApiError
import ru.mypackage.nmedia.error.NetworkError
import ru.mypackage.nmedia.error.UnknownError
import java.io.IOException

private const val RESULT_SUCCESS = "Success"
private const val RESULT_ERROR = "Error"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = GoodsAdapter()
        binding.rvGoods.adapter = adapter

        lifecycleScope.launch {
            try {
                val response = GoodsApi.retrofitService.getGoods()
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }
                val body = response.body() ?: throw ApiError(response.code(), response.message())

                when (body.status) {
                    RESULT_SUCCESS -> binding.rvGoods.visibility = View.VISIBLE
                    RESULT_ERROR -> binding.rvGoods.visibility = View.GONE
                    else -> binding.rvGoods.visibility = View.GONE
                }

                adapter.submitList(body.TOVARY[0].data)

                val tablayout = binding.tabLayout

                tablayout.addTab(tablayout.newTab().setText(body.TOVARY[0].NAME), 0)
                tablayout.addTab(tablayout.newTab().setText(body.TOVARY[1].NAME), 1)
                tablayout.addTab(tablayout.newTab().setText(body.TOVARY[2].NAME), 2)

                tablayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        when (tab?.position) {
                            0 -> adapter.submitList(body.TOVARY[0].data)
                            1 -> adapter.submitList(body.TOVARY[1].data)
                            2 -> adapter.submitList(body.TOVARY[2].data)
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }

                })

            } catch (e: IOException) {
                throw NetworkError
            } catch (e: Exception) {
                throw UnknownError
            }
        }
    }
}