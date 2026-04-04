package com.example.fragments.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

class RetrofitClient {
    companion object {
        private var mItemAPI: ProductService? = null

        @Synchronized
        fun API(): ProductService {
            if (mItemAPI == null) {
                val gsondateformat = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()

                mItemAPI = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gsondateformat))
                    .baseUrl("https://69d1527c90cd06523d5e05be.mockapi.io/")
                    .build()
                    .create(ProductService::class.java)
            }
            return mItemAPI!!
        }
    }
}