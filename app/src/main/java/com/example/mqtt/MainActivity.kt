package com.example.mqtt

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mqtt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val PERMISSIONS_REQUEST_CODE = 123
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissions()
        initbuttons()
    }

    private fun checkPermissions() {
        val wifiPermission = Manifest.permission.ACCESS_WIFI_STATE
        val bluetoothPermission = Manifest.permission.BLUETOOTH

        val wifiPermissionGranted = ContextCompat.checkSelfPermission(this, wifiPermission) == PackageManager.PERMISSION_GRANTED
        val bluetoothPermissionGranted = ContextCompat.checkSelfPermission(this, bluetoothPermission) == PackageManager.PERMISSION_GRANTED

        if (!wifiPermissionGranted || !bluetoothPermissionGranted) {
            ActivityCompat.requestPermissions(this, arrayOf(wifiPermission, bluetoothPermission), PERMISSIONS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            var allPermissionsGranted = true
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false
                    break
                }
                else{
                    creator()
                }
            }
        }
    }

    private fun creator(){
        val robotArm = RobotArm()

        val command = "some_command"
        robotArm.interpretCommand(command)

        val position = "some_position"
        robotArm.setPosition(position)

        val gesture = "some_gesture"
        robotArm.performGesture(gesture)

        val connector = Connector(context = applicationContext)
    }

    private fun initbuttons() {
        binding.btnMode2.setOnClickListener{
            Toast.makeText(applicationContext, "Mode2 активирован", Toast.LENGTH_SHORT).show()
        }

        binding.btnMode1.setOnClickListener{
            Toast.makeText(applicationContext, "Mode1 активирован", Toast.LENGTH_SHORT).show()
        }

        binding.btnConnect.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null)
            val btnConnect = dialogView.findViewById<Button>(R.id.btnConnect)

            builder.setView(dialogView)
                .setPositiveButton(null, null)
            val alertDialog = builder.create()

            btnConnect.setOnClickListener {
                Toast.makeText(applicationContext, "Подключено: esp8266", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
            }
            alertDialog.show()

        }

        binding.btnDisconnect.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            val dialogView = LayoutInflater.from(this).inflate(R.layout.negative_dialog_layout, null)
            val btnDisconnect = dialogView.findViewById<Button>(R.id.btn_cancel)
            val btnCancel = dialogView.findViewById<Button>(R.id.btn_confirm)

            builder.setView(dialogView)
                .setPositiveButton(null, null)
            val alertDialog = builder.create()


            btnCancel.setOnClickListener {
                alertDialog.dismiss()
            }

            btnDisconnect.setOnClickListener {
                Toast.makeText(applicationContext, "Отключено: esp8266", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
    }
}
