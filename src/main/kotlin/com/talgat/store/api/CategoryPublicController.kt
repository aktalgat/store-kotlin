package com.talgat.store.api

import com.talgat.store.data.model.Category
import com.talgat.store.data.service.CategoryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryPublicController(private val categoryService: CategoryService) : AbstractPublicApi() {

    companion object {
        val log : Logger = LoggerFactory.getLogger(CategoryPublicController::class.java)
    }

    @GetMapping("/categories")
    fun getCategoryPublicList(): List<Category> {
        log.info("Public api. Request for getting all categories")

        return categoryService.getCategoryList()
    }
}