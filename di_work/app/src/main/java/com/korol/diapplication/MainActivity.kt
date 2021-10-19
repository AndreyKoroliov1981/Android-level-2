package com.korol.diapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.korol.diapplication.classes.Bicycle
import com.korol.diapplication.classes.MySimplePresenter
import org.koin.android.ext.android.inject

import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var myBicycle: Bicycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val buttonDagger: Button = findViewById(R.id.dagger)
        val buttonKoin: Button = findViewById(R.id.koin)
        buttonDagger.setOnClickListener {
            App.appComponent.injectMainActivity(this)
            Toast.makeText(applicationContext, "Pressed Dagger button. Bicycle Created $myBicycle", Toast.LENGTH_SHORT).show()

        }
        buttonKoin.setOnClickListener {
            val firstPresenter: MySimplePresenter by inject()
            Toast.makeText(applicationContext, "Pressed Koin button. Bicycle Created ${firstPresenter.sayHello()}", Toast.LENGTH_SHORT).show()
        }

    }
}