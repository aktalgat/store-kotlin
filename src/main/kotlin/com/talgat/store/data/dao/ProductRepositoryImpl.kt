package com.talgat.store.data.dao

import com.talgat.store.data.model.Product
import com.talgat.store.data.model.ProductImage
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Connection
import java.sql.Statement

@Repository
@Transactional
class ProductRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : ProductRepository {
    private val deleteStatus = 1
    private val selectQuery = "SELECT id, category_id, name, description, short_description, additional_info, " +
            "badge, price, price_old, stars FROM core.products p WHERE status=0 " +
            "AND (SELECT status FROM core.categories WHERE id=p.category_id)=0"
    private val selectImageQuery = "SELECT id, product_id, url FROM core.product_images"
    private val insertQuery = "INSERT INTO core.products(category_id, name, description, short_description, " +
            "additional_info, badge, price, price_old, stars) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
    private val insertImageQuery = "INSERT INTO core.product_images(product_id, url) VALUES (?, ?)"

    private val deleteQuery = "UPDATE core.products SET status=? WHERE id=?"
    private val updateQuery = "UPDATE core.products SET category_id=?, name=?, description=?, short_description=?, " +
            "additional_info=?, badge=?, price=?, price_old=?, stars=? WHERE id=?"
    private val updateImageQuery = "UPDATE core.product_images SET url=? WHERE id=?"

    override fun save(product: Product): Product {
        val holder = GeneratedKeyHolder()
        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)
            ps.setLong(1, product.categoryId)
            ps.setString(2, product.name)
            ps.setString(3, product.description)
            ps.setString(4, product.shortDescription)
            ps.setString(5, product.additionalInfo)
            ps.setString(6, product.badge)
            ps.setDouble(7, product.price)
            ps.setDouble(8, product.priceOld)
            ps.setInt(9, product.stars)
            ps
        }, holder)
        val keys = holder.keys
        val id: Long = keys?.get("id").toString().toLong()

        val list = product.productImageList.map {
            arrayOf(id, it.url)
        }
        jdbcTemplate.batchUpdate(insertImageQuery, list)

        return product.copy(id = id)
    }

    override fun findAll(): List<Product> {
        val list = jdbcTemplate.query(selectQuery) { rs, _ ->
                    Product(rs.getLong("id"), rs.getLong("category_id"), rs.getString("name"),
                    rs.getString("description"), rs.getString("short_description"), rs.getString("additional_info"),
                    rs.getString("badge"), rs.getDouble("price"), rs.getDouble("price_old"), rs.getInt("stars"), listOf())
                }

        val imageList = jdbcTemplate.query(selectImageQuery) { rs, _ ->
            ProductImage(rs.getLong("id"), rs.getLong("product_id"), rs.getString("url"))
        }

        return list.map {
            val newImageList: List<ProductImage> = imageList.filter { item -> item.productId == it.id }
            it.copy(productImageList = newImageList)
        }
    }

    override fun delete(id: Long): Boolean {
        val updatedRowCount = jdbcTemplate.update { connection: Connection ->
            val ps = connection.prepareStatement(deleteQuery)
            ps.setInt(1, deleteStatus)
            ps.setLong(2, id)
            ps
        }
        return updatedRowCount == 1
    }

    override fun update(product: Product): Product {
        jdbcTemplate.update { connection ->
            val ps = connection.prepareStatement(updateQuery)
            ps.setLong(1, product.categoryId)
            ps.setString(2, product.name)
            ps.setString(3, product.description)
            ps.setString(4, product.shortDescription)
            ps.setString(5, product.additionalInfo)
            ps.setString(6, product.badge)
            ps.setDouble(7, product.price)
            ps.setDouble(8, product.priceOld)
            ps.setInt(9, product.stars)
            ps.setLong(10, product.id)
            ps
        }

        product.productImageList.filter { it.id != 0L } .forEach {
            jdbcTemplate.update {connection ->
                val ps = connection.prepareStatement(updateImageQuery)
                ps.setString(1, it.url)
                ps.setLong(2, it.id)
                ps
            }
        }

        val list = product.productImageList.filter {it.id == 0L} .map {
            arrayOf(product.id, it.url)
        }
        jdbcTemplate.batchUpdate(insertImageQuery, list)

        return product
    }
}