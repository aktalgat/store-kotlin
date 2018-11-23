package com.talgat.store.model

data class Product(
        val id: Long,
        val categoryId: Long,
        val name: String,
        val description: String,
        val shortDescription: String,
        val additionalInfo: String,
        val badge: String,
        val price: Double,
        val priceOld: Double,
        val stars: Int,
        val productImageList: List<ProductImage>
)