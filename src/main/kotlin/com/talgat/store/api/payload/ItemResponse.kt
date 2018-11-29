package com.talgat.store.api.payload

data class ItemResponse(
        val message: String,
        val id: Long) : AbstractApiResponse(message)