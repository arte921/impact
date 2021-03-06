package arte921.impact

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var gsensor: Sensor? = null
    private var fres: Float = 0.0F
    private var max: Float = 0.0F
    private val fz: Float = 9.81F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val current = findViewById<TextView>(R.id.current)
        val record = findViewById<TextView>(R.id.record)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent){
        fres = round((sqrt(event.values[0].pow(2)+event.values[1].pow(2)+event.values[2].pow(2))-fz).absoluteValue*10)/10

        current.text = "current: $fres"
        if(fres > max){
            max = fres
            record.text = "record: $max"
        }

    }

    override fun onResume(){
        super.onResume()
        gsensor?.also{gs ->
            sensorManager.registerListener(this,gs,SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    public fun resetMax(view: View){
        max = 0.0F
    }

}
