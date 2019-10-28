package com.vironit.shoppingcart.utils

import android.content.Context
import android.net.ConnectivityManager

fun Context.isInternetEnabled(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo?.isConnected ?: false
}