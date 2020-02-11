package com.vironit.shoppingcart.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vironit.shoppingcart.BuildConfig
import com.vironit.shoppingcart.data.model.Product

@Database(entities = [Product::class], version = BuildConfig.DATABASE_VERSION, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}