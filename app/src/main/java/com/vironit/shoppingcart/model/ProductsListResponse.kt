package com.vironit.shoppingcart.model

import com.google.gson.annotations.SerializedName

data class ProductsListResponse(
    @SerializedName("products")
    val products: List<Product>
)