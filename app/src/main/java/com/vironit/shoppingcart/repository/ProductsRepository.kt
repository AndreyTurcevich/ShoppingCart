package com.vironit.shoppingcart.repository

import com.vironit.shoppingcart.data.database.ProductsDao
import com.vironit.shoppingcart.data.model.Product
import com.vironit.shoppingcart.data.network.API

class ProductsRepository(
    private val api: API,
    private val productsDao: ProductsDao
) : BaseRepository() {

    suspend fun getProductsList() = safeApiCall(
        call = { api.getProductsListAsync().await() },
        errorMessage = "Error Fetching Products List"
    )?.products?.toMutableList()

    suspend fun getProductDetails(productId: String) = safeApiCall(
        call = { api.getProductDetailsAsync(productId).await() },
        errorMessage = "Error Fetching Product Details"
    )

    suspend fun getProductsListFromDB(): MutableList<Product> {
        return productsDao.getProducts().toMutableList()
    }

    suspend fun getProductFromDB(productId: String): Product {
        return productsDao.getProduct(productId)
    }

    suspend fun insertProductsIntoDB(products: List<Product>) {
        productsDao.insertProducts(products)
    }

    suspend fun updateProductInDB(product: Product) {
        productsDao.updateProduct(product)
    }

}