package ru.company.listofgoods.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.company.listofgoods.R
import ru.company.listofgoods.api.GoodsApi
import ru.company.listofgoods.databinding.ActivityMainBinding
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

        lifecycleScope.launch {
            try {
                val response = GoodsApi.retrofitService.getGoods()
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }
                val body = response.body() ?: throw ApiError(response.code(), response.message())
                binding.tvStatus.setText(body.status)
                binding.tvData.text = body.TOVARY.get(0).NAME
                when (body.status) {
                    RESULT_SUCCESS -> binding.tvData.visibility = View.VISIBLE
                    RESULT_ERROR -> binding.tvData.visibility = View.GONE
                    else -> binding.tvData.text = ""
                }

            } catch (e: IOException) {
                throw NetworkError
            } catch (e: Exception) {
                throw UnknownError
            }
        }
    }
}