@file:Suppress("DEPRECATION")

package com.xoxoer.lifemarklibrary

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.lifecycle.LiveData

/*
 * Created at 03/07/2020.
 * Created by Dimas Prasetya
 * Modified and Enhanced by Muhammad Isa
 * Please tell me when you have problem with this code. (prasetya.dimas95@gmail.com)
*/

class Lifemark constructor(private val context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    // forever check
    inner class ObservableNetworkCondition: LiveData<Boolean>() {

        override fun onActive() {
            super.onActive()
            updateConnection()
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                    connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback())
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    lollipopNetworkRequest()
                }
                else -> {
                    context.registerReceiver(
                        networkReceiver,
                        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                    )
                }
            }
        }

        override fun onInactive() {
            super.onInactive()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                connectivityManager.unregisterNetworkCallback(connectivityManagerCallback())
            else
                context.unregisterReceiver(networkReceiver)
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private fun lollipopNetworkRequest() {
            val requestBuilder = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)

            connectivityManager.registerNetworkCallback(
                requestBuilder.build(),
                connectivityManagerCallback()
            )
        }

        private fun connectivityManagerCallback(): ConnectivityManager.NetworkCallback {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                networkCallback = object : ConnectivityManager.NetworkCallback() {
                    override fun onLost(network: Network) {
                        super.onLost(network)
                        postValue(false)
                    }

                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        postValue(true)
                    }
                }
                return networkCallback
            } else throw IllegalAccessError("Error")
        }

        private val networkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                updateConnection()
            }
        }

        private fun updateConnection() {
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            postValue(activeNetwork?.isConnected == true)
        }

    }

    // on execution check
    fun isNetworkConnected(): Boolean {
        var result = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }

}