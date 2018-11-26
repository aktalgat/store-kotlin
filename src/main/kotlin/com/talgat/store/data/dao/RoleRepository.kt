package com.talgat.store.data.dao

import com.talgat.store.data.model.Role
import com.talgat.store.data.model.RoleName
import java.util.*

interface RoleRepository {
    fun findByName(roleName: RoleName): Role?
}