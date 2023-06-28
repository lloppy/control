package com.example.mqtt

import android.util.Log
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

class WifiServer (private val broker: String, private val clientId: String) {
    private lateinit var mqttClient: MqttClient

    private fun connect() {
        try {
            val persistence = MemoryPersistence()
            mqttClient = MqttClient(broker, clientId, persistence)

            val options = MqttConnectOptions()
            options.isCleanSession = true

            mqttClient.connect(options)
        } catch (ex: MqttException) {
            ex.printStackTrace()
        }
    }

    private fun sendCommand(topic: String, command: String) {
        try {
            mqttClient.publish(topic, command.toByteArray(), 0, false)
        } catch (ex: MqttException) {
            ex.printStackTrace()
        }
    }

    private fun receiveSignal(topic: String, callback: MqttCallbackExtended) {
        try {
            mqttClient.subscribe(topic)
            mqttClient.setCallback(callback)
        } catch (ex: MqttException) {
            ex.printStackTrace()
        }
    }

    private fun handleNotification(notification: String) {
        Log.e("notification", "notification is $notification")
    }
}
