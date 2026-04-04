package com.example.fragments.repository

import com.example.fragments.model.Product
import com.example.fragments.network.RetrofitClient
import retrofit2.Response


class ProductRepository {

    suspend fun getProducts(): Response<List<Product>> = RetrofitClient.API().getProducts()
    suspend fun getProductById(id: Int): Response<Product> = RetrofitClient.API().getProductById(id)
    suspend fun createProduct(p: Product): Response<Product> = RetrofitClient.API().createProduct(p)
    suspend fun updateProduct(id: String, p: Product): Response<Product> =
        RetrofitClient.API().updateProduct(id, p)
    suspend fun deleteProduct(id: String): Response<Unit> = RetrofitClient.API().deleteProduct(id)
}
