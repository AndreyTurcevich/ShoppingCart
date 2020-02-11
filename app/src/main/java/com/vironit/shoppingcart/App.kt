package com.vironit.shoppingcart

import android.app.Application

import androidx.room.Room

import com.vironit.shoppingcart.data.database.Database

class App : Application() {

    lateinit var database: Database
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, Database::class.java, "database")
            .build()
    }

    companion object {
        lateinit var instance: App
    }
}