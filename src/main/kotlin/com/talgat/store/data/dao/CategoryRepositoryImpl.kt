package com.talgat.store.data.dao

import com.talgat.store.data.model.Category
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class CategoryRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : CategoryRepository {
    private val query = "SELECT id, name FROM core.categories"

    override fun findAll(): List<Category> {
        return jdbcTemplate.query(query) {rs, _ ->
            Category(rs.getString("name"), rs.getLong("id"))
        }
    }

    override fun save(category: Category): Category {
        TODO("not implemented")
    }

}