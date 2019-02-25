package com.talgat.store.data.dao

import com.talgat.store.data.model.Category
import com.talgat.store.exception.InternalException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Connection
import java.sql.Statement

@Repository
@Transactional
class CategoryRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : CategoryRepository {
    private val query = "SELECT id, name, blocked FROM core.categories"
    private val saveQuery = "INSERT INTO core.categories(name) VALUES (?)"
    private val updateQuery = "UPDATE core.categories set name=?, blocked=? WHERE id=?"
    private val queryById = "SELECT id, name, blocked FROM core.categories WHERE id=?"

    override fun findAll(): List<Category> {
        return jdbcTemplate.query(query) {rs, _ ->
            Category(rs.getString("name"), rs.getLong("id"), rs.getBoolean("blocked"))
        }
    }

    override fun save(category: Category): Category {
        val holder = GeneratedKeyHolder()
        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(saveQuery, Statement.RETURN_GENERATED_KEYS)
            ps.setString(1, category.name)
            ps
        }, holder)

        val keys = holder.keys
        val id: Long = keys?.get("id").toString().toLong()
        return category.copy(id = id)
    }

    override fun findById(id: Long): Category {
        return jdbcTemplate.query(queryById) {rs, _ ->
            Category(rs.getString("name"), rs.getLong("id"), rs.getBoolean("blocked"))
        }[0]
    }

    override fun update(category: Category) {
        jdbcTemplate.update { connection ->
            val ps = connection.prepareStatement(updateQuery)
            ps.setString(1, category.name)
            ps.setBoolean(2, category.blocked)
            ps.setLong(3, category.id)
            ps
        }
    }
}