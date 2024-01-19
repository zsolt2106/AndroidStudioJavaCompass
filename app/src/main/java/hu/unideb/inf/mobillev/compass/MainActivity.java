package hu.unideb.inf.mobillev.compass;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView degreetxt;
    TextView steptxt;

    ImageView compassimg;
    SensorManager sensorManager;

    private int stepCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        degreetxt = findViewById(R.id.tvDegrees);
        compassimg = findViewById(R.id.imgCompass);
        steptxt = findViewById(R.id.tvSteps);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensorList) {
            Log.d("Sensor", s.toString());  // In case i need a specific sensor for something. Otherwise can be deleted.
        }
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER));
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int degree = Math.round(sensorEvent.values[0]);
        //int steps = (int) sensorEvent.values[0];   // If you wanna see ALL the steps the sensor has counted so far (today), then just uncomment this line and change the 'stepCount' variable below.
        degreetxt.setText("Heading to: " + degree + " ° ");
        compassimg.setRotation(-degree);
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            stepCount++;
            steptxt.setText("You have taken " + stepCount + " steps!");

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

