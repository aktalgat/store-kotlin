package com.talgat.store.api

import com.talgat.store.data.model.Product
import com.talgat.store.data.service.ProductService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductPublicController(private val productService: ProductService) : AbstractPublicApi() {

    companion object {
        val log : Logger = LoggerFactory.getLogger(ProductPublicController::class.java)
    }

    @GetMapping("/products")
    fun getProductPublicList() : List<Product> {
        log.info("Public api. Request for getting all products")

        return productService.getProductList()
    }
}
