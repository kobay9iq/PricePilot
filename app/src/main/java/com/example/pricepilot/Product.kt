package com.example.pricepilot

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Product(
	val picUrl: String? = null,
	val marketName: String? = null,
	val storeURL: String? = null,
	val productUrl: String? = null,
	val sameProductsUrl: String? = null,
	val productName: String? = null,
	val productPrice: String? = null,
) {
	@Transient
	var isLiked = false
}

