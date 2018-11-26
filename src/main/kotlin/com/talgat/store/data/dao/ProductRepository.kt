package com.talgat.store.data.dao

import com.talgat.store.data.model.Product

interface ProductRepository {
    fun findAll(): List<Product>
}
