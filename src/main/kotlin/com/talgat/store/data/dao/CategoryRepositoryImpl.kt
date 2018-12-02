package com.talgat.store.data.dao

import com.talgat.store.data.model.Category
import com.talgat.store.exception.InternalException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class CategoryRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : CategoryRepository {
    private val query = "SELECT id, name FROM core.categories"
    private val saveQuery = "INSERT INTO core.categories(name) VALUES (?)"

    override fun findAll(): List<Category> {
        return jdbcTemplate.query(query) {rs, _ ->
            Category(rs.getString("name"), rs.getLong("id"))
        }
    }

    override fun save(category: Category): Category {
        val holder = GeneratedKeyHolder()
        jdbcTemplate.update(saveQuery, arrayOf(category.name), holder)
        val id = holder.key?.toLong() ?: throw InternalException("Not inserted id for category")
        return category.copy(id = id)
    }

}