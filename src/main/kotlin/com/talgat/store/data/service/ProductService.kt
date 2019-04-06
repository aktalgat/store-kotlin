package com.talgat.store.data.service

import com.talgat.store.api.payload.ProductRequest
import com.talgat.store.data.model.Product

interface ProductService {
    fun getProductList(): List<Product>
    fun saveProduct(productRequest: ProductRequest): Product
    fun saveProduct(product: Product): Product
    fun deleteProduct(id: Long): Boolean
}
