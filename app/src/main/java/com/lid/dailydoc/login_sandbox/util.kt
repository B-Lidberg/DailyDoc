package com.lid.dailydoc.login_sandbox

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
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
@RequiresApi(Build.VERSION_CODES.M)
private fun ConnectivityManager.isConnected(): Boolean {
    val capabilities = getNetworkCapabilities(activeNetwork) ?: return false
    val wifiConnected = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    val mobileDataActive = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    val ethernetConnected = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    return wifiConnected || mobileDataActive || ethernetConnected
}

@RequiresApi(Build.VERSION_CODES.M)
fun Context.isConnected(): Boolean {
    return (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).isConnected()
}

@RequiresApi(Build.VERSION_CODES.M)
fun Context.connectedOrThrow() {
    if (!isConnected()) throw Exception("You are offline")
}