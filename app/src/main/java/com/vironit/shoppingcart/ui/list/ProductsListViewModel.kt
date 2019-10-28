package com.vironit.shoppingcart.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vironit.shoppingcart.database.Database
import com.vironit.shoppingcart.model.Product
import com.vironit.shoppingcart.network.ApiFactory
import com.vironit.shoppingcart.repository.ProductsRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProductsListViewModel : ViewModel() {

    // Public Data

    val productsListLiveData = MutableLiveData<MutableList<Product>>()

    val isLoading = MutableLiveData<Boolean>()

    lateinit var database: Database

    // Internal Data

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository: ProductsRepository by lazy {
        ProductsRepository(
            ApiFactory.api,
            database.productsDao()
        )
    }

    // Public methods

    override fun onCleared() {
        super.onCleared()
        cancelAllRequests()
    }

    fun fetchProductsList(isInternetEnabled: Boolean) {
        scope.launch {
            isLoading.postValue(true)
            val productsList = if (isInternetEnabled) {
                repository.getProductsList()
            } else {
                repository.getProductsListFromDB()
            }
            productsListLiveData.postValue(productsList)
            if (!productsList.isNullOrEmpty()) {
                repository.insertProductsIntoDB(productsList.toList())
            }
            isLoading.postValue(false)
        }
    }

    // Internal logic

    private fun cancelAllRequests() = coroutineContext.cancel()
}
