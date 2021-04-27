package com.lid.dailydoc.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.CancellationException
import kotlin.coroutines.resumeWithException

@Suppress("UNCHECKED_CAST", "EXPERIMENTAL_API_USAGE")
suspend fun <T> Task<T>.await(): T {
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) throw CancellationException("Task $this was cancelled normally.")
            else result as T
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                if (isCanceled) cont.cancel() else cont.resume(result as T) {}
            } else {
                cont.resumeWithException(e)
            }
        }
    }
}

private fun ConnectivityManager.isConnected(): Boolean {
    val capabilities = getNetworkCapabilities(activeNetwork) ?: return false
    val wifiConnected = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    val mobileDataActive = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    val ethernetConnected = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    return wifiConnected || mobileDataActive || ethernetConnected
}

fun Context.isConnected(): Boolean {
    return (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).isConnected()
}

fun Context.connectedOrThrow() {
    if (!isConnected()) throw Exception("You are offline")
}