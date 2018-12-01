package com.talgat.store.data.dao

import com.talgat.store.data.model.Product
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ProductRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : ProductRepository {
    private val query = "SELECT id, category_id, name, description, short_description, additional_info, " +
                    "badge, price, price_old, stars FROM core.products"

    override fun save(product: Product): Product {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): List<Product> {
        return jdbcTemplate.query(query) { rs, _ ->
                    Product(rs.getLong("id"), rs.getLong("category_id"), rs.getString("name"),
                    rs.getString("description"), rs.getString("short_description"), rs.getString("additional_info"),
                    rs.getString("badge"), rs.getDouble("price"), rs.getDouble("price_old"), rs.getInt("stars"), listOf())
                }
    }
}