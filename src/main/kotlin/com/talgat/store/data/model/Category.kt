package com.talgat.store.data.model

data class Category(
        val name: String,
        val id: Long = 0,
        val blocked: Boolean = false
) {
    constructor(name: String) : this(name, 0)
}