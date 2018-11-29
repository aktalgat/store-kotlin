package com.talgat.store.api

import com.talgat.store.api.payload.CategoryRequest
import com.talgat.store.api.payload.ItemResponse
import com.talgat.store.data.model.Category
import com.talgat.store.data.service.CategoryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class CategoryProtectedController(val categoryService: CategoryService) : AbstractProtectedApi() {

    companion object {
        val log : Logger = LoggerFactory.getLogger(CategoryProtectedController::class.java)
    }

    @GetMapping("/categories")
    fun getCategoryList(): List<Category> {
        log.info("Request for getting all categories")

        return categoryService.getCategoryList()
    }

    @PostMapping("/categories")
    fun saveCategory(@Valid @RequestBody categoryRequest: CategoryRequest): ItemResponse {
        log.info("Request for saving category")

        val category = categoryService.saveCategory(categoryRequest)

        return ItemResponse("Category saved", category.id)
    }

    @PutMapping("/categories")
    fun updateCategory(@Valid @RequestBody categoryRequest: CategoryRequest): ItemResponse {
        log.info("Request for update category")

        return ItemResponse("Category updated", 0L)
    }
}