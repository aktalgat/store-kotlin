package com.talgat.store.api

import com.talgat.store.api.payload.ItemResponse
import com.talgat.store.api.payload.ProductRequest
import com.talgat.store.data.model.Product
import com.talgat.store.data.service.ProductService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class ProductProtectedController(private val productService: ProductService): AbstractProtectedApi() {

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
        val product = productService.saveProduct(productRequest)

        return ItemResponse("Product saved", product.id)
    }

    @PutMapping("/products/{id}")
    fun updateProduct(@Valid @RequestBody productRequest: ProductRequest, @PathVariable id: Long): ItemResponse {
        log.info("Request for updating product")
        log.info("Product request: {}", productRequest)

        return ItemResponse("Product updated", id)
    }

    @DeleteMapping("/products/{id}")
    fun deleteProduct(@PathVariable id: Long): ItemResponse {
        log.info("Request for deleting product")
        log.info("Delete product request: {}", id)
        val deleted = productService.deleteProduct(id)

        return ItemResponse(if (deleted) "Product deleted" else "Not deleted", id)
    }
}
