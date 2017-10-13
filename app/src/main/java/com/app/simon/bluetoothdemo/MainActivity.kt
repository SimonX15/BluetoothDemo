package com.app.simon.bluetoothdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.simon.bluetoothlib.BluetoothHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var bluetoothHelper: BluetoothHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        assignViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothHelper!!.unRegisterReceiver()
    }

    private fun init() {

        bluetoothHelper = BluetoothHelper(this)
        bluetoothHelper!!.registerReceiver()
    }

    private fun assignViews() {
        btn_open.setOnClickListener {
            bluetoothHelper!!.open()
        }

        btn_close.setOnClickListener {
            bluetoothHelper!!.close()
        }
    }

}
