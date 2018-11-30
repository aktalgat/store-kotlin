package com.talgat.store.api

import com.talgat.store.api.payload.ItemResponse
import com.talgat.store.api.payload.ProductRequest
import com.talgat.store.data.model.Product
import com.talgat.store.data.service.ProductService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class ProductProtectedController(private val productService: ProductService) : AbstractProtectedApi() {

    companion object {
        val log : Logger = LoggerFactory.getLogger(ProductProtectedController::class.java)
    }

    @GetMapping("/products")
    fun getProductList(): List<Product> {
        log.info("Request for getting all products")

        return productService.getProductList()
    }

    @PostMapping("/products")
    fun saveProduct(@Valid @RequestBody productRequest: ProductRequest): ItemResponse {
        log.info("Request for saving product")
        log.info("Product request: {}", productRequest)
        val (id) = productService.saveProduct(productRequest)

        return ItemResponse("Product saved", id)
    }
}
