package arte921.impact

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var gsensor: Sensor? = null
    private var fres: Float = 0.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tva = findViewById<TextView>(R.id.tva)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        //tva.text = "howdy"
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent){
        fres = sqrt(event.values[0].pow(2)+event.values[1].pow(2)+event.values[2].pow(2))
        tva.text = fres.toString()
    }

    override fun onResume(){
        super.onResume()
        gsensor?.also{gs ->
            sensorManager.registerListener(this,gs,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}
