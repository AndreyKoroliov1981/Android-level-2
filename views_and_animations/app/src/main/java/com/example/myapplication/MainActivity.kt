package com.example.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.customview.AnalogClock
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.schedule
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentTime = findViewById<TextView>(R.id.currenttime)
        val myTime = findViewById<AnalogClock>(R.id.myTimer)
        val btnStartStop=findViewById<Button>(R.id.buttonStartStop)
        val btnReset=findViewById<Button>(R.id.buttonReset)

        var c = Calendar.getInstance()
        var sdf = SimpleDateFormat("HH:mm:ss")
        var strDate = sdf.format(c.time)
        currentTime.text = strDate


        // Запускаем поток отображения текущего времени
        fixedRateTimer("timer", false, 0L, 1000) {
            this@MainActivity.runOnUiThread {
                c = Calendar.getInstance()
                sdf = SimpleDateFormat("HH:mm:ss")
                strDate = sdf.format(c.time)
                currentTime.text = strDate

            }
        }

        btnStartStop.setOnClickListener {
            if (myTime.isPlayed) {
                btnStartStop.text="Start"
                myTime.stop()
            } else {
                btnStartStop.text="Stop"
                myTime.start()
            }

        }

        btnReset.setOnClickListener {
            myTime.reset()
            btnStartStop.text="Start"
        }


    }

}