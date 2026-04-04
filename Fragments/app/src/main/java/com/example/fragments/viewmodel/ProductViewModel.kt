package com.example.fragments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fragments.model.Product
import com.example.fragments.repository.ProductRepository
import kotlinx.coroutines.launch


class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()


    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products


    private val _selectedProduct = MutableLiveData<Product?>()
    val selectedProduct: LiveData<Product?> = _selectedProduct


    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message


    fun selectProduct(p: Product) { _selectedProduct.value = p }

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val r = repository.getProducts()
                if (r.isSuccessful) _products.value = r.body() ?: emptyList()
                else _message.value = "Error: ${r.code()}"
            } catch (e: Exception) {
                _message.value = "Sin conexion: ${e.message}"
            } finally { _isLoading.value = false }
        }
    }


    fun createProduct(p: Product) {
        viewModelScope.launch {
            try {
                val r = repository.createProduct(p)
                if (r.isSuccessful) { _message.value = "Producto creado"; loadProducts() }
                else _message.value = "Error al crear: ${r.code()}"
            } catch (e: Exception) { _message.value = "Error: ${e.message}" }
        }
    }


    fun updateProduct(id: String, p: Product) {
        viewModelScope.launch {
            try {
                val r = repository.updateProduct(id, p)
                if (r.isSuccessful) { _message.value = "Actualizado"; loadProducts() }
                else _message.value = "Error al actualizar: ${r.code()}"
            } catch (e: Exception) { _message.value = "Error: ${e.message}" }
        }
    }


    fun deleteProduct(id: String) {
        viewModelScope.launch {
            try {
                val r = repository.deleteProduct(id)
                if (r.isSuccessful) { _message.value = "Eliminado"; loadProducts() }
                else _message.value = "Error al eliminar: ${r.code()}"
            } catch (e: Exception) { _message.value = "Error: ${e.message}" }
        }
    }
}
