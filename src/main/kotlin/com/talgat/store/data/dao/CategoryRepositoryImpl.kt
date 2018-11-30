package com.talgat.store.data.dao

import com.talgat.store.data.model.Category
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class CategoryRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : CategoryRepository {
    override fun findAll(): List<Category> {
        TODO("not implemented")
    }

    override fun findById(id: Long): Category? {
        TODO("not implemented")
    }

    override fun save(category: Category): Category {
        TODO("not implemented")
    }

}