package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.customview.AnalogClock
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.schedule
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity() {

    lateinit var saveTimer:AnalogClock.TimeState
    lateinit var buttonText:String
    private val saveKey="SAVE_KEY"
    private val saveKey2="SAVE_KEY2"
    //var myTime2 = AnalogClock

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val myTime2=findViewById<AnalogClock>(R.id.myTimer)
        saveTimer=AnalogClock.TimeState(myTime2.currentTime(),myTime2.isPlayed)
        outState.putSerializable(saveKey,saveTimer)
        val btnStartStop=findViewById<Button>(R.id.buttonStartStop)
        buttonText=btnStartStop.text.toString()
        outState.putString(saveKey2,buttonText)

    }

    override  fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        saveTimer= savedInstanceState.getSerializable(saveKey) as AnalogClock.TimeState
        val myTime=findViewById<AnalogClock>(R.id.myTimer)
        myTime.myCurrentTime=saveTimer.time
        myTime.isPlayed=saveTimer.isPlayed
        if (myTime.isPlayed) myTime.start() else myTime.stop()
        val str=savedInstanceState.getString(saveKey2)
        val btnStartStop=findViewById<Button>(R.id.buttonStartStop)
        btnStartStop.text=str

    }

}