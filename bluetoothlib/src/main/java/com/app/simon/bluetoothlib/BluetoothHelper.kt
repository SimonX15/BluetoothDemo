package com.app.simon.bluetoothlib

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log


/**
 * desc: BluetoothHelper
 * date: 2017/10/13

 * @author xw
 */
object BluetoothHelper {



    fun test(context: Context) {
        //首先获取BluetoothManager
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        //获取BluetoothAdapter
        bluetoothManager.adapter

        // 用BroadcastReceiver来取得搜索结果
        val intent = IntentFilter()
        intent.addAction(BluetoothDevice.ACTION_FOUND)//搜索发现设备
        intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)//状态改变
        intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)//行动扫描模式改变了
        intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)//动作状态发生了变化
        context.registerReceiver(getReceiver(), intent)
    }

    /**
     * 蓝牙接收广播
     */
    fun getReceiver(): BroadcastReceiver {

        val searchDevices = object : BroadcastReceiver() {
            //接收
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                val b = intent.extras
                val lstName = b.keySet().toTypedArray()

                // 显示所有收到的消息及其细节
                for (i in lstName.indices) {
                    val keyName = lstName[i].toString()
                    Log.e("bluetooth", keyName + ">>>" + b.get(keyName).toString())
                }
                val device: BluetoothDevice

                when (action) {
                // 搜索发现设备时，取得设备的信息；注意，这里有可能重复搜索同一设备
                    BluetoothDevice.ACTION_FOUND -> {
                        device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                        Log.d("BlueToothTestActivity", "ACTION_FOUND......")
//                        onRegisterBltReceiver.onBluetoothDevice(device)
                    }
                //状态改变时
                    BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                        device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                        when (device.bondState) {
                            BluetoothDevice.BOND_BONDING//正在配对
                            -> {
                                Log.d("BlueToothTestActivity", "正在配对......")
//                                onRegisterBltReceiver.onBltIng(device)
                            }
                            BluetoothDevice.BOND_BONDED//配对结束
                            -> {
                                Log.d("BlueToothTestActivity", "完成配对")
//                                onRegisterBltReceiver.onBltEnd(device)
                            }
                            BluetoothDevice.BOND_NONE//取消配对/未配对
                            -> {
                                Log.d("BlueToothTestActivity", "取消配对")
//                                onRegisterBltReceiver.onBltNone(device)
                            }
                            else -> {
                            }
                        }
                    }
                }
            }
        }
        return searchDevices
    }
}
