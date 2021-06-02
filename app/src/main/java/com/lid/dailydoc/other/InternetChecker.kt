package com.lid.dailydoc.other

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Min SDK is currently set to 26
 * SDK Marshmallow(API Level 23) and above use the following.
 * For sdk under Marshmallow... Can use:
 * return connectivityManager.activeNetworkInfo?.isAvailable ?: false
 */

fun checkForInternetConnection(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
            else -> false
    }
}