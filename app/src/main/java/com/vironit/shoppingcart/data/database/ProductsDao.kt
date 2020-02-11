package com.vironit.shoppingcart.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.vironit.shoppingcart.data.model.Product

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products")
    suspend fun getProducts(): List<Product>

    @Query("SELECT * FROM products WHERE product_id =:productId")
    suspend fun getProduct(productId: String): Product

    @Insert(onConflict = REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Update
    suspend fun updateProduct(user: Product)


}