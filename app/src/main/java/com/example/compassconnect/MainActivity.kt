package com.example.compassconnect

import android.app.usage.UsageEvents.Event
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity(),SensorEventListener {

    var sensor: Sensor?=null
    var currentDegree= 0f  //to keep track how the compass is rotating
    var sensorManager:SensorManager?=null
    lateinit var compassImage:ImageView
    lateinit var rotateTV:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor=sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)

        compassImage=findViewById(R.id.imageView2)
        rotateTV=findViewById(R.id.textView)

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        var degree= Math.round(p0!!.values[0])
        rotateTV.text=degree.toString() + "degrees"
        var rotationAnimation= RotateAnimation(currentDegree, (-degree).toFloat(),Animation.RELATIVE_TO_SELF,
            0.5f,Animation.RELATIVE_TO_SELF,0.5f)
        rotationAnimation.duration=210
        rotationAnimation.fillAfter=true

        compassImage.startAnimation(rotationAnimation)
        currentDegree= (-degree).toFloat()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }
}
