package com.example.alarm2

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import androidx.core.app.NotificationCompat


class RingtoneService : Service() {
    companion object {
        lateinit var r: Ringtone
    }

    var id: Int = 0
    var isRunning: Boolean = false
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val state: String? = intent!!.getStringExtra("extra")
        assert(state != null)
        when (state) {
            "on" -> id = 1
            "off" -> id = 0
        }
        if (!this.isRunning && id == 1) {

            playAlarm()
            this.isRunning = true
            this.id = 0
            startNotification()

        } else if (this.isRunning && id == 0){

            r.stop()
            this.isRunning = false
            this.id = 0

        } else if (!this.isRunning && id == 0){

            this.isRunning = false
            this.id = 0

        } else if (this.isRunning && id == 1){

            this.isRunning = false
            this.id = 1

        } else {}

        return START_NOT_STICKY
    }

    private fun startNotification() {
        val mainActivityIntent = Intent(this,MainActivity::class.java)
        val pi:PendingIntent = PendingIntent.getActivity(this,0,mainActivityIntent,0)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notifyManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification:Notification = NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(defaultSoundUri)
            .setContentIntent(pi)
            .setAutoCancel(true)
            .build()

        notifyManager.notify(0,notification)
    }

    private fun playAlarm() {
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        r = RingtoneManager.getRingtone(baseContext, alarmUri)
        r.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.isRunning = false
    }

}