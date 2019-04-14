package com.talgat.store.data.service

import com.talgat.store.api.payload.ProductRequest
import com.talgat.store.data.dao.ProductRepository
import com.talgat.store.data.model.Product
import com.talgat.store.data.model.ProductImage
import com.talgat.store.exception.InternalException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {

    companion object {
        val log : Logger = LoggerFactory.getLogger(ProductServiceImpl::class.java)
    }

    fun createProduct(productRequest: ProductRequest): Product {
        val list = ArrayList<ProductImage>()
        for (url in productRequest.productImageList) {
            list.add(ProductImage(0, 0, url))
        }
        return Product(0, productRequest.categoryId, productRequest.name, productRequest.description,
                productRequest.shortDescription, productRequest.additionalInfo,
                productRequest.badge, productRequest.price, productRequest.priceOld,
                productRequest.stars, list)
    }

    override fun saveProduct(productRequest: ProductRequest): Product {
        return saveProduct(createProduct(productRequest))
    }

    override fun saveProduct(product: Product): Product {
        var newProduct: Product?
        try {
            newProduct = productRepository.save(product)
        } catch (e: Exception) {
            log.error("error: {}", e.message)
            throw InternalException(e.message ?: "Internal error in saving")
        }

        log.info("Saved product: {}", newProduct)

        return newProduct
    }

    override fun getProductList(): List<Product> {
        return productRepository.findAll()
    }

    override fun deleteProduct(id: Long): Boolean {
        return productRepository.delete(id)
    }

    override fun updateProduct(productRequest: ProductRequest): Product {
        return productRepository.update(createProduct(productRequest))
    }
}