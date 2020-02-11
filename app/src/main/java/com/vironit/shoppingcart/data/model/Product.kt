package com.vironit.shoppingcart.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class Product(
    @SerializedName("product_id")
    @ColumnInfo(name = "product_id")
    @PrimaryKey var productId: String,
    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String?,
    @SerializedName("description")
    @ColumnInfo(name = "description")
    var description: String?,
    @SerializedName("price")
    @ColumnInfo(name = "price")
    var price: Int?,
    @SerializedName("image")
    @ColumnInfo(name = "image")
    var image: String?
)