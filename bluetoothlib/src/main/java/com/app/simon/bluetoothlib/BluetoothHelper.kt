package com.app.simon.bluetoothlib

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Settings
import android.util.Log
import org.jetbrains.anko.toast


/**
 * desc: BluetoothHelper
 * date: 2017/10/13

 * @author xw
 */
class BluetoothHelper(val context: Context) {

    private val TAG = BluetoothHelper::class.java.simpleName

    /** 蓝牙操作 */
    private var mBluetoothAdapter: BluetoothAdapter? = null

    /** 广播监听 */
    private val bluetoothBroadcastService = BluetoothBroadcastService()

    /** 是否正在搜索 */
    private var isScanning = false

    init {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        //获取BluetoothAdapter
        mBluetoothAdapter = bluetoothManager.adapter
    }

    /** 打开蓝牙 */
    fun open(): Unit {
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "该手机不支持蓝牙")
            context.toast("该手机不支持蓝牙")
            return
        }
        if (!mBluetoothAdapter!!.isEnabled) {
            val enableBtIntent = Intent()
            //第一种，到设置页面
            enableBtIntent.action = Settings.ACTION_BLUETOOTH_SETTINGS
            //第二种，直接在页面点击开启
//            enableBtIntent.action = BluetoothAdapter.ACTION_REQUEST_ENABLE
            enableBtIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(enableBtIntent)
            //第三种，直接开启，需要权限
//            mBluetoothAdapter!!.enable()
        } else {
            Log.i(TAG, "蓝牙已打开")
            context.toast("蓝牙已打开")
        }
    }

    /** 关闭蓝牙 */
    fun close(): Unit {
        mBluetoothAdapter?.disable()
    }

    /** 开始搜索 */
    fun startSearchDevice(): Boolean {
//        if (mBluetoothAdapter!!.isDiscovering) {
//
//        }
        mBluetoothAdapter!!.startDiscovery()
        //这里的true并不是代表搜索到了设备，而是表示搜索成功开始。
        return true
    }

    /** 停止搜索 */
    fun stopSearchDevice(): Boolean {
        return mBluetoothAdapter!!.cancelDiscovery()
    }

    /** 注册 */
    fun registerReceiver() {
        val intent = IntentFilter()
        intent.addAction(BluetoothDevice.ACTION_FOUND)//搜索发现设备
        intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)//状态改变
        intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)//行动扫描模式改变了
        intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)//动作状态发生了变化
        context.registerReceiver(bluetoothBroadcastService, intent)
    }

    /** 取消注册 */
    fun unRegisterReceiver() {
        context.unregisterReceiver(bluetoothBroadcastService)
        //取消蓝牙的配对
        mBluetoothAdapter?.cancelDiscovery()
    }
}
