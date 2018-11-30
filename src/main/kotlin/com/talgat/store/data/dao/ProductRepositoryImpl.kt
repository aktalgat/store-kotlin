package com.talgat.store.data.dao

import com.talgat.store.data.model.Product
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ProductRepositoryImpl(val jdbcTemplate: JdbcTemplate) : ProductRepository {
    override fun save(product: Product): Product {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): List<Product> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}