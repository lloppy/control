package com.example.mqtt

import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

class RobotArm {
    private val serverUri = "tcp://mqtt_server_ip:1883" // MQTT
    private val clientId = "RobotArmClient"
    private val topicPrefix = "robot/arm"

    private lateinit var mqttClient: MqttClient

    init {
        try {
            mqttClient = MqttClient(serverUri, clientId, MemoryPersistence())
            mqttClient.connect()
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun interpretCommand(command: String) {
        publishMessage("$topicPrefix/command", command)
    }

    fun setPosition(position: String) {
        publishMessage("$topicPrefix/position", position)
    }

    fun performGesture(gesture: String) {
        publishMessage("$topicPrefix/gesture", gesture)
    }

    private fun publishMessage(topic: String, message: String) {
        try {
            val mqttMessage = MqttMessage(message.toByteArray())
            mqttClient.publish(topic, mqttMessage)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}
