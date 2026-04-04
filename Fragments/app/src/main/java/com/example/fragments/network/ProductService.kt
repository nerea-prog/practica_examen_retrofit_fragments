package com.example.fragments.network
import com.example.fragments.model.Product
import retrofit2.Response
import retrofit2.http.*


interface ProductService {


    // GET /products  -> devuelve lista completa
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>


    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<Product>


    @POST("products")
    suspend fun createProduct(@Body product: Product): Response<Product>


    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: String,
        @Body product: Product
    ): Response<Product>


    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<Unit>
}
