package ru.company.listofgoods.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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

                binding.tvFirst.text = body.TOVARY[0].NAME
                binding.tvSecond.text = body.TOVARY[1].NAME
                binding.tvThird.text = body.TOVARY[2].NAME

                binding.tvFirst.setOnClickListener {
                    adapter.submitList(body.TOVARY[0].data)
                }

                binding.tvSecond.setOnClickListener {
                    adapter.submitList(body.TOVARY[1].data)
                }

                binding.tvThird.setOnClickListener {
                    adapter.submitList(body.TOVARY[2].data)
                }

            } catch (e: IOException) {
                throw NetworkError
            } catch (e: Exception) {
                throw UnknownError
            }
        }
    }
}