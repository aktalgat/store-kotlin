package com.talgat.store.data.dao

import com.talgat.store.data.model.Product
import com.talgat.store.data.model.ProductImage
import com.talgat.store.exception.InternalException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ProductRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : ProductRepository {
    private val query = "SELECT id, category_id, name, description, short_description, additional_info, " +
                    "badge, price, price_old, stars FROM core.products"
    private val imageQuery = "SELECT id, product_id, url FROM core.product_images"
    private val saveQuery = "INSERT INTO core.products(category_id, name, description, short_description, " +
            "additional_info, badge, price, price_old, stars) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
    private val imageInsertQuery = "INSERT INTO core.product_images(product_id, url) VALUES (?, ?)"

    override fun save(product: Product): Product {
        val holder = GeneratedKeyHolder()
        jdbcTemplate.update(saveQuery, arrayOf(product.categoryId, product.name, product.description,
                product.description, product.shortDescription, product.additionalInfo, product.badge, product.price,
                product.priceOld, product.stars), holder)
        val id: Long = holder.key?.toLong() ?: throw InternalException("Not inserted id fo product")

        val list = product.productImageList.map {
            arrayOf(id, it.url)
        }
        jdbcTemplate.batchUpdate(imageInsertQuery, list)

        return product.copy(id = id)
    }

    override fun findAll(): List<Product> {
        val list = jdbcTemplate.query(query) { rs, _ ->
                    Product(rs.getLong("id"), rs.getLong("category_id"), rs.getString("name"),
                    rs.getString("description"), rs.getString("short_description"), rs.getString("additional_info"),
                    rs.getString("badge"), rs.getDouble("price"), rs.getDouble("price_old"), rs.getInt("stars"), listOf())
                }

        val imageList = jdbcTemplate.query(imageQuery) {rs, _ ->
            ProductImage(rs.getLong("id"), rs.getLong("product_id"), rs.getString("url"))
        }

        return list.map {
            val newImageList: List<ProductImage> = imageList.filter { item -> item.productId == it.id }
            it.copy(productImageList = newImageList)
        }
    }
}