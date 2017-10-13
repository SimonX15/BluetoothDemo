package com.app.simon.bluetoothlib

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * desc: 蓝牙接收广播
 * date: 2017/10/13
 *
 * @author xw
 */
class BluetoothBroadcastService : BroadcastReceiver() {

    private val TAG = BluetoothBroadcastService::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        val bundle = intent?.extras
//        val lstName = bundle?.keySet()?.toTypedArray()

        // 显示所有收到的消息及其细节
//        lstName?.forEach {
//            Log.i(TAG, "name foreach：" + it + ">>>" + bundle.get(it)?.toString())
//        }

        val device: BluetoothDevice

        when (action) {
        // 搜索发现设备时，取得设备的信息;注意，这里有可能重复搜索同一设备
            BluetoothDevice.ACTION_FOUND -> {
                device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                Log.d(TAG, "发现设备：${"address=" + device.address + "  ;name=" + device.name}")
//                        onRegisterBltReceiver.onBluetoothDevice(device)
            }
        //状态改变时
            BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                when (device.bondState) {
                //正在配对
                    BluetoothDevice.BOND_BONDING -> {
                        Log.d(TAG, "正在配对：${"address=" + device.address + "  ;name=" + device.name}")
//                                onRegisterBltReceiver.onBltIng(device)
                    }
                //配对结束
                    BluetoothDevice.BOND_BONDED -> {
                        Log.d(TAG, "完成配对：${"address=" + device.address + "  ;name=" + device.name}")
//                                onRegisterBltReceiver.onBltEnd(device)
                    }
                //取消配对/未配对
                    BluetoothDevice.BOND_NONE -> {
                        Log.d(TAG, "取消配对：${"address=" + device.address + "  ;name=" + device.name}")
//                                onRegisterBltReceiver.onBltNone(device)
                    }
                    else -> {
                    }
                }
            }
        }
    }
}