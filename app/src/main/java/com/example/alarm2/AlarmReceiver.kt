package com.example.alarm2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        var getResult: String? = intent!!.getStringExtra("extra")

        var serviceIntent = Intent(context, RingtoneService::class.java)
        serviceIntent.putExtra("extra", getResult)
        context!!.startService(serviceIntent)
    }
}