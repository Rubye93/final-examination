package com.example.alarm2

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var am: AlarmManager
    lateinit var tp: TimePicker
    lateinit var updateText: TextView
    lateinit var con: Context
    lateinit var btnStart: Button
    lateinit var btnStop: Button
    var hour: Int = 0
    var min: Int = 0
    lateinit var pi: PendingIntent
    lateinit var bottomNavigationView: BottomNavigationView

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.fragment_one -> {
                    fragment = PageOne()
                }
                R.id.fragment_two -> {
                    fragment = PageTwo()

                }

            }
            replaceFragments(fragment!!)
            true
        }

        this.con = this
        am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        tp = findViewById(R.id.tp)
        updateText = findViewById(R.id.update_text)
        btnStop = findViewById(R.id.stop_alarm)
        btnStart = findViewById(R.id.set_alarm)

        val calendar: Calendar = Calendar.getInstance()
        val myIntent = Intent(this, AlarmReceiver::class.java)

        btnStart.setOnClickListener {
            calendar.set(Calendar.HOUR_OF_DAY, tp.hour)
            calendar.set(Calendar.MINUTE, tp.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            hour = tp.hour
            min = tp.minute

            var hrStr: String = hour.toString()
            var minStr: String = min.toString()
            if (hour > 12)
                hrStr = (hour - 12).toString()
            if (min < 10)
                minStr = "0$min"

            setAlarmText("Alarm set to: $hrStr : $minStr")
            myIntent.putExtra("extra", "on")
            pi = getBroadcast(this@MainActivity, 0, myIntent, FLAG_UPDATE_CURRENT)
            am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pi)
        }

        btnStop.setOnClickListener {
            setAlarmText("Alarm off")
            pi = getBroadcast(this@MainActivity, 0, myIntent, FLAG_UPDATE_CURRENT)
            am.cancel(pi)
            myIntent.putExtra("extra", "off")
            sendBroadcast(myIntent)
        }
    }

    private fun setAlarmText(s: String) {
        updateText.text = s
    }

    fun replaceFragments(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


}