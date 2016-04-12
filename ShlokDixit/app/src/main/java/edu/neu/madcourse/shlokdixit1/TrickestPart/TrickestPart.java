package edu.neu.madcourse.shlokdixit1.TrickestPart;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;

import edu.neu.madcourse.shlokdixit1.R;

public class TrickestPart extends Activity {

    private SensorManager mSensorManager;

    private Burpeecheck mSensorListener;
    int count;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trickest_part);
        final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new Burpeecheck();
        mSensorListener.setOnShakeListener(new Burpeecheck.OnShakeListener() {

            public void onShake()                  {
                count ++;
                if(count == 10) {
                    num++;
                    TextView Burpee = (TextView) findViewById(R.id.Burpee);
                    Burpee.setText(String.valueOf(num));
                    count = 0;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}
