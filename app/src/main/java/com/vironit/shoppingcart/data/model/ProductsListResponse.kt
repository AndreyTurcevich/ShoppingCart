package com.vironit.shoppingcart.data.model

import com.google.gson.annotations.SerializedName

data class ProductsListResponse(
    @SerializedName("products")
    val products: List<Product>
)