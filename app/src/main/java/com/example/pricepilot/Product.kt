package com.example.pricepilot

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.pricepilot.ProductDbContract.ProductsDb
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Entity(tableName = ProductDbContract.ProductsDb.TABLE_NAME,
	indices = [Index(ProductDbContract.ProductsDb.COLOMN_NAME_ID)])
data class Product(

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = ProductDbContract.ProductsDb.COLOMN_NAME_ID)
	@Transient
	val id: Long = 0,

	@ColumnInfo(name = ProductDbContract.ProductsDb.COLOMN_NAME_PRODUCT_PIC_URL)
	val picUrl: String? = null,

	@ColumnInfo(name = ProductDbContract.ProductsDb.COLOMN_NAME_STORE)
	val marketName: String? = null,

	@ColumnInfo(name = ProductDbContract.ProductsDb.COLOMN_NAME_STORE_URL)
	val storeURL: String? = null,

	@ColumnInfo(name = ProductDbContract.ProductsDb.COLOMN_NAME_PRODUCT_URL)
	val productUrl: String? = null,

	@ColumnInfo(name = ProductDbContract.ProductsDb.COLOMN_NAME_SIMILAR_PRODUCTS)
	val sameProductsUrl: String? = null,

	@ColumnInfo(name = ProductDbContract.ProductsDb.COLOMN_NAME_TITLE)
	val productName: String? = null,

	@ColumnInfo(name = ProductDbContract.ProductsDb.COLOMN_NAME_PRICE)
	val productPrice: String? = null,

	@ColumnInfo(name = ProductDbContract.ProductsDb.COLOMN_NAME_IS_LIKED)
	@Transient
	var isLiked: Boolean = false
)

