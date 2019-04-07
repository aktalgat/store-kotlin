package com.talgat.store.data.service

import com.talgat.store.api.payload.CategoryRequest
import com.talgat.store.data.model.Category

interface CategoryService {
    fun getCategoryList(): List<Category>
    fun saveCategory(categoryRequest: CategoryRequest): Category
    fun saveCategory(category: Category): Category
    fun updateCategory(categoryRequest: CategoryRequest): Category
    fun deleteCategory(id: Long): Boolean
}