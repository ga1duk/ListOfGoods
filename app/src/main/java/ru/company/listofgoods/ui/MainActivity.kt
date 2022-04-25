package ru.company.listofgoods.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.company.listofgoods.R
import ru.company.listofgoods.api.GoodsApi
import ru.company.listofgoods.databinding.ActivityMainBinding
import ru.mypackage.nmedia.error.ApiError
import ru.mypackage.nmedia.error.NetworkError
import ru.mypackage.nmedia.error.UnknownError
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
                try {
                    val response = GoodsApi.retrofitService.getGoods()
                    if (!response.isSuccessful) {
                        throw ApiError(response.code(), response.message())
                    }
                    val body = response.body() ?: throw ApiError(response.code(), response.message())
                    binding.tvStatus.setText(body.status)
                } catch (e: IOException) {
                    throw NetworkError
                } catch (e: Exception) {
                    throw UnknownError
                }
            }
        }
    }