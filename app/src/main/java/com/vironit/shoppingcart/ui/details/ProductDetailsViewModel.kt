package com.vironit.shoppingcart.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vironit.shoppingcart.database.Database
import com.vironit.shoppingcart.model.Product
import com.vironit.shoppingcart.network.ApiFactory
import com.vironit.shoppingcart.repository.ProductsRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProductDetailsViewModel : ViewModel() {

    // Public Data

    val productLiveData = MutableLiveData<Product>()

    val isLoading = MutableLiveData<Boolean>()

    lateinit var database: Database

    // Internal Data

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository: ProductsRepository by lazy {
        ProductsRepository(ApiFactory.api, database.productsDao())
    }

    // Public methods

    override fun onCleared() {
        super.onCleared()
        cancelAllRequests()
    }

    fun fetchProduct(productId: String, isInternetEnabled: Boolean) {
        scope.launch {
            isLoading.postValue(true)
            val product =
                if (isInternetEnabled)
                    repository.getProductDetails(productId)
                else
                    repository.getProductFromDB(productId)
            if (product != null) {
                repository.updateProductInDB(product)
            }
            productLiveData.postValue(product)
            isLoading.postValue(false)
        }
    }

    // Internal logic

    private fun cancelAllRequests() = coroutineContext.cancel()
}