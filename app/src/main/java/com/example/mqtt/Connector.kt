package com.example.mqtt

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log

class Connector(val context: Context) {

    init {

        val connectionManager = ConnectionManager(context)

        connectionManager.scanNetworks()

        val networkName = "your_network_name"
        val password = "your_password"
        val connectSuccess = connectionManager.connectToNetwork(networkName, password)
        if (connectSuccess) {
            // Успешно подключено к Wi-Fi сети
        } else {
            // Не удалось подключиться к Wi-Fi сети
        }


        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val connectionInfo = wifiManager.connectionInfo
        val isConnected = connectionInfo.networkId != -1
        connectionManager.handleConnectionEvent(isConnected)

        Log.e("connection", "isConnected in connector")

    }
}
