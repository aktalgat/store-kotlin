package com.talgat.store.data.service

import com.talgat.store.api.payload.CategoryRequest
import com.talgat.store.data.dao.CategoryRepository
import com.talgat.store.data.model.Category
import com.talgat.store.exception.InternalException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(private val categoryRepository: CategoryRepository) : CategoryService {

    companion object {
        val log : Logger = LoggerFactory.getLogger(CategoryServiceImpl::class.java)
    }

    override fun saveCategory(categoryRequest: CategoryRequest): Category {
        val category = Category(categoryRequest.name)

        return saveCategory(category)
    }

    override fun saveCategory(category: Category): Category {
        val newCategory: Category?
        try {
            newCategory = categoryRepository.save(category)
        } catch (e: Exception) {
            throw InternalException(e.message ?: "Internal error in saving")
        }

        log.info("Saved category: {}", newCategory)
        return newCategory
    }

    override fun getCategoryList(): List<Category> {
        return categoryRepository.findAll()
    }

    override fun updateCategory(categoryRequest: CategoryRequest): Category {
        val category = Category(categoryRequest.name, categoryRequest.id)
        categoryRepository.update(category)

        log.info("Updated category: {}", category)
        return category

    }

    override fun deleteCategory(id: Long): Boolean {
        return categoryRepository.delete(id)
    }
}
