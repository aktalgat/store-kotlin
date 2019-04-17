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
class CategoryProtectedController(private val categoryService: CategoryService) : AbstractProtectedApi() {

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

    @PutMapping("/categories/{id}")
    fun updateCategory(@Valid @RequestBody categoryRequest: CategoryRequest, @PathVariable id: Long): ItemResponse {
        log.info("Request for update category")
        log.info("Category for update {}", categoryRequest)

        categoryService.updateCategory(categoryRequest)

        return ItemResponse("Category updated", id)
    }

    @DeleteMapping("/categories/{id}")
    fun deleteCategory(@PathVariable id: Long): ItemResponse {
        log.info("Request for deleting category")
        log.info("Delete category request: {}", id)
        val deleted = categoryService.deleteCategory(id)

        return ItemResponse(if (deleted) "Category deleted" else "Not deleted", id)
    }
}