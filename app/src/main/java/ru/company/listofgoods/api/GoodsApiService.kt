package ru.company.listofgoods.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import ru.company.listofgoods.BuildConfig
import ru.company.listofgoods.dto.GoodsModel

private const val BASE_URL = "${BuildConfig.BASE_URL}/newmobile/glavnaya/"

val logging = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

private val okhttp = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .client(okhttp)
    .baseUrl(BASE_URL)
    .build()

interface GoodsApiService {
    @GET("super_top.php?action=topglav")
    suspend fun getGoods(): Response<GoodsModel>
}

object GoodsApi {
    val retrofitService: GoodsApiService by lazy {
        retrofit.create(GoodsApiService::class.java)
    }
}