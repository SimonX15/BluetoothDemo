package com.app.simon.bluetoothdemo

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.simon.bluetoothlib.BluetoothBroadcastService

class MainActivity : AppCompatActivity() {

    var bluetoothBroadcastService: BluetoothBroadcastService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bluetoothBroadcastService = BluetoothBroadcastService()

        //首先获取BluetoothManager
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        //获取BluetoothAdapter
        bluetoothManager.adapter

        registerReceiver()

    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterReceiver()
    }

    fun registerReceiver() {
        // 用BroadcastReceiver来取得搜索结果
        val intent = IntentFilter()
        intent.addAction(BluetoothDevice.ACTION_FOUND)//搜索发现设备
        intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)//状态改变
        intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)//行动扫描模式改变了
        intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)//动作状态发生了变化
        registerReceiver(bluetoothBroadcastService, intent)
    }

    fun unRegisterReceiver() {
        unregisterReceiver(bluetoothBroadcastService)

    }
}
