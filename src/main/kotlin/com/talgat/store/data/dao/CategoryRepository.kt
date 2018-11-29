package com.talgat.store.data.dao

import com.talgat.store.data.model.Category

interface CategoryRepository {
    fun findAll(): List<Category>
    fun findById(id: Long): Category?
    fun save(category: Category): Category
}
