package com.talgat.store.data.service

import com.talgat.store.api.payload.ProductRequest
import com.talgat.store.data.dao.ProductRepository
import com.talgat.store.data.model.Product
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

    override fun saveProduct(productRequest: ProductRequest): Product {
        val product = Product(0, productRequest.categoryId, productRequest.name, productRequest.description,
                productRequest.shortDescription, productRequest.additionalInfo,
                productRequest.badge, productRequest.price, productRequest.priceOld,
                productRequest.stars, ArrayList())
        /*val list: ImmutableList<ProductImage> = product.productImageList
        for (url in productRequest.productImageList) {
            list.add(ProductImage(0, url))
        }*/

        return saveProduct(product)
    }

    override fun saveProduct(product: Product): Product {
        var newProduct: Product?
        try {
            newProduct = productRepository.save(product)
        } catch (e: Exception) {
            log.error("error: {}", e.message)
            throw InternalException("Internal error in saving")
        }

        log.info("Saved product: {}", newProduct)

        return newProduct
    }

    override fun getProductList(): List<Product> {
        return productRepository.findAll()
    }
}