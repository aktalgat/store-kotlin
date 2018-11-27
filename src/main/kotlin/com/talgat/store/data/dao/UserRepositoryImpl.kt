package com.talgat.store.data.dao

import com.talgat.store.data.model.Role
import com.talgat.store.data.model.RoleName
import com.talgat.store.data.model.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.ArrayList

@Repository
@Transactional
class UserRepositoryImpl(val jdbcTemplate: JdbcTemplate) : UserRepository {

    private val userQuery = "SELECT id, phone, first_name, last_name, password, email, enabled FROM core.users"
    private val roleQuery = "SELECT r.id, r.name FROM core.user_roles ur INNER JOIN core.roles r on ur.role_id=r.id"

    override fun findByPhone(phone: String): User? {
        val userList : List<User> = jdbcTemplate.query("$userQuery where u.phone=?", arrayOf<Any>(phone)) {rs, _ ->
            User(rs.getLong("id"), rs.getString("phone"), rs.getString("email"),
                    rs.getString("first_name"), rs.getString("last_name"), rs.getString("password"),
                    rs.getBoolean("enabled"), ArrayList()) }

        if (!userList.isEmpty()) return null

        val roleList: List<Role> = jdbcTemplate.query("$roleQuery where ur.user_id=?", arrayOf<Any>(userList[0].id)) {
            rs, _ -> Role(rs.getLong("id"), RoleName.valueOf(rs.getString("name")))
        }

        return userList[0].copy(roles = roleList)
    }

    override fun save(user: User) {

    }

    override fun findById(id: Long): User? {

        return null
    }
}
