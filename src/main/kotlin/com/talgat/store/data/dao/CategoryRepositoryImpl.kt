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
    private val deleteStatus = 1
    private val query = "SELECT id, name FROM core.categories WHERE status=0"
    private val saveQuery = "INSERT INTO core.categories(name) VALUES (?)"
    private val updateQuery = "UPDATE core.categories SET name=? WHERE id=?"
    private val queryById = "SELECT id, name FROM core.categories WHERE id=?"
    private val deleteQuery = "UPDATE core.categories SET status=? WHERE id=?"

    override fun findAll(): List<Category> {
        return jdbcTemplate.query(query) {rs, _ ->
            Category(rs.getString("name"), rs.getLong("id"))
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
            Category(rs.getString("name"), rs.getLong("id"))
        }[0]
    }

    override fun update(category: Category) {
        jdbcTemplate.update { connection ->
            val ps = connection.prepareStatement(updateQuery)
            ps.setString(1, category.name)
            ps.setLong(2, category.id)
            ps
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
}