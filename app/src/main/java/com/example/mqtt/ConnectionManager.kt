package com.example.mqtt

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.util.Log

class ConnectionManager(private val context: Context) {
    private val wifiManager: WifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    @SuppressLint("MissingPermission")
    fun scanNetworks() {
        wifiManager.startScan()
        val scanResults = wifiManager.scanResults
           }

    fun connectToNetwork(networkName: String, password: String): Boolean {
        val wifiConfig = WifiConfiguration()
        wifiConfig.SSID = "\"$networkName\""
        wifiConfig.preSharedKey = "\"$password\""

        val networkId = wifiManager.addNetwork(wifiConfig)
        if (networkId != -1) {
            wifiManager.disconnect()
            wifiManager.enableNetwork(networkId, true)
            val reconnectSuccess = wifiManager.reconnect()
            return reconnectSuccess
        }
        return false
    }

    fun handleConnectionEvent(isConnected: Boolean) {
        if (isConnected) {
            Log.e("connection", "isConnected is $isConnected")
        } else {
            Log.e("connection", "isConnected is $isConnected")
            Log.e("connection", "isConnected error!")
        }
    }
}